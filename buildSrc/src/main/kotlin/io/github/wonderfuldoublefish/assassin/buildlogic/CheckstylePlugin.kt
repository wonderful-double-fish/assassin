package io.github.wonderfuldoublefish.assassin.buildlogic

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin as GradleCheckstylePlugin
import java.io.File

/**
 * 【角色插件 / Binary Plugin】Assassin Checkstyle 代码规范检查。
 *
 * **默认直接使用 Checkstyle 官方内置规范，仓库里不需要自己维护 xml。**
 * Checkstyle 的 jar 里打包了两份现成规范，插件会注册一个 `prepareCheckstyleConfig`
 * 任务把它们取出来落成 `build/checkstyle/<规范名>`，checkstyle 任务依赖该任务
 * （所以 `clean` 之后重跑也能正常）。
 *  - `google_checks.xml` —— Google Java Style（默认）
 *  - `sun_checks.xml`    —— Sun/Oracle 官方编码规范
 *
 * 换规范 / 用自己的文件：加构建参数（`gradle.properties` 或命令行 `-P`）
 * ```
 * # 用 Sun 官方规范
 * assassin.checkstyle.config=sun_checks.xml
 * # 用自己的规范文件（相对仓库根目录）
 * assassin.checkstyle.config=config/checkstyle/checkstyle.xml
 * # 严格模式：违规直接让构建失败（默认只报告、不拦构建）
 * assassin.checkstyle.strict=true
 * # 临时跳过
 * assassin.checkstyle.skip=true
 * ```
 *
 * 为什么默认不拦构建：官方规范（尤其 Google）对存量代码非常严格（Javadoc 首句必须
 * 完整句子、缩进、final 参数、行宽 100 等），直接 fail 会让 `./gradlew build` 长期红着。
 * 默认行为是「照常检查 + 生成报告 + 打印违规，但构建继续」，等代码清理干净后把
 * `assassin.checkstyle.strict=true` 写进 gradle.properties 即可转为强制。
 *
 * 另外：
 *  - 应用 `checkstyle` 插件，`checkstyleMain` / `checkstyleTest` 自动挂到 `check`
 *  - 工具版本从构建 classpath 上的 checkstyle 依赖读取（只在 libs.versions.toml 声明一次）
 *  - 输出 XML + HTML 报告：`<module>/build/reports/checkstyle/{main,test}.html`
 *
 * 个别位置可在代码里用 `@SuppressWarnings("checkstyle:RuleName")` 局部抑制。
 *
 * 使用方式：`plugins { id("assassin.checkstyle") }`（`assassin.module` / `assassin.service`
 * 已代为引入，单独使用只在特殊模块里需要）
 */
class CheckstylePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.apply(GradleCheckstylePlugin::class.java)

        val configured = project.providers
            .gradleProperty(CONFIG_PROPERTY)
            .orElse(DEFAULT_CONFIG_NAME)
            .get()
        val skip = project.providers
            .gradleProperty(SKIP_PROPERTY)
            .map { it.toBoolean() }
            .getOrElse(false)
        val strict = project.providers
            .gradleProperty(STRICT_PROPERTY)
            .map { it.toBoolean() }
            .getOrElse(false)

        val builtInRuleset = BUILT_IN_RULESETS[configured.lowercase()]
        val configFile = if (builtInRuleset == null) {
            // 用户自定义规范：相对仓库根目录的路径，必须真实存在
            project.rootProject.layout.projectDirectory.file(configured).asFile.also { file ->
                require(file.isFile) {
                    "checkstyle 规范文件不存在：$file（内置规范可写 google / sun，或写相对仓库根目录的路径）"
                }
            }
        } else {
            // 官方内置规范：注册任务把它从 checkstyle jar 落到 build 目录
            val prepare = project.tasks.register(
                PREPARE_TASK,
                PrepareCheckstyleConfig::class.java,
                Action<PrepareCheckstyleConfig> {
                    this.rulesetResource.set(builtInRuleset)
                    this.outputDirectory.set(project.layout.buildDirectory.dir(CHECKSTYLE_BUILD_DIR))
                },
            )
            val file = project.layout.buildDirectory
                .get()
                .dir(CHECKSTYLE_BUILD_DIR)
                .file(builtInRuleset)
                .asFile
            project.tasks.withType(Checkstyle::class.java).configureEach(
                Action<Checkstyle> {
                    this.dependsOn(prepare)
                },
            )
            file
        }

        val toolVersion = checkstyleVersion()

        project.extensions.configure(
            CheckstyleExtension::class.java,
            Action<CheckstyleExtension> {
                this.toolVersion = toolVersion
                this.configFile = configFile
                if (strict) {
                    // 严格模式：任何违规（含 warning 级）都让构建失败
                    this.maxWarnings = 0
                    this.maxErrors = 0
                } else {
                    // 默认模式：只报告，不拦构建（保留 Checkstyle 的默认阈值）
                    this.maxWarnings = Int.MAX_VALUE
                    this.maxErrors = Int.MAX_VALUE
                }
            },
        )

        project.tasks.withType(Checkstyle::class.java).configureEach(
            Action {
                this.enabled = !skip
                this.reports.xml.required.set(true)
                this.reports.html.required.set(true)
            },
        )
    }

    private companion object {
        /** 覆盖规范的构建参数名。 */
        const val CONFIG_PROPERTY = "assassin.checkstyle.config"

        /** 跳过检查的构建参数名。 */
        const val SKIP_PROPERTY = "assassin.checkstyle.skip"

        /** 严格模式（违规即失败）的构建参数名。 */
        const val STRICT_PROPERTY = "assassin.checkstyle.strict"

        /** 默认规范：Google Java Style。 */
        const val DEFAULT_CONFIG_NAME = "google_checks.xml"

        /** 官方内置规范：构建参数值（小写）→ checkstyle jar 内资源名。 */
        val BUILT_IN_RULESETS = mapOf(
            "google" to "google_checks.xml",
            "google_checks.xml" to "google_checks.xml",
            "sun" to "sun_checks.xml",
            "sun_checks.xml" to "sun_checks.xml",
        )

        /** 官方规范落地目录（相对模块构建目录）。 */
        const val CHECKSTYLE_BUILD_DIR = "checkstyle"

        /** 提取官方规范的任务名。 */
        const val PREPARE_TASK = "prepareCheckstyleConfig"

        /**
         * 从构建 classpath 上已加载的 checkstyle 依赖读取版本号，
         * 避免「版本目录」与「插件代码」两处维护。
         */
        fun checkstyleVersion(): String =
            com.puppycrawl.tools.checkstyle.Main::class.java
                .`package`
                .implementationVersion
                ?: error("无法从 checkstyle 依赖读取版本号，请检查 buildSrc/build.gradle.kts 中 libs.checkstyle 的声明")
    }
}

/**
 * 把 Checkstyle jar 里的官方规范提取到模块构建目录。
 *
 * 说明：Checkstyle 官方规范文件带 DOCTYPE，必须让 Checkstyle 以「文件」形式加载
 * （它自己会把 DTD 解析到 jar 内同名资源），所以这里落到真实文件再交给 configFile。
 */
abstract class PrepareCheckstyleConfig : org.gradle.api.DefaultTask() {

    @get:org.gradle.api.tasks.Input
    abstract val rulesetResource: org.gradle.api.provider.Property<String>

    @get:org.gradle.api.tasks.OutputDirectory
    abstract val outputDirectory: org.gradle.api.file.DirectoryProperty

    @org.gradle.api.tasks.TaskAction
    fun extract() {
        val resourceName = rulesetResource.get()
        val target = File(outputDirectory.get().asFile, resourceName)
        val resource = javaClass.classLoader.getResourceAsStream(resourceName)
            ?: error("checkstyle jar 里找不到内置规范 $resourceName，请检查 checkstyle 依赖版本")
        resource.use { input ->
            target.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        logger.lifecycle("checkstyle 规范已就绪：$target")
    }
}
