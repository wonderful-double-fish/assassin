package io.github.wonderfuldoublefish.assassin.buildlogic

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.logging.LogLevel
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin as GradleCheckstylePlugin
import java.io.File


class CheckstylePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.apply(GradleCheckstylePlugin::class.java)

        val catalog = project.rootProject
            .extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named(LIBRARY_CATALOG)

        val checkstyleVersion = catalog.findVersion("checkstyle")
            .orElseThrow { error("版本目录里找不到 checkstyle 版本，请检查 gradle/libs.versions.toml") }
            .displayName
        val springJavaformatVersion = catalog.findVersion("spring-javaformat")
            .orElseThrow { error("版本目录里找不到 spring-javaformat 版本，请检查 gradle/libs.versions.toml") }
            .displayName

        project.extensions.configure(
            CheckstyleExtension::class.java,
            Action<CheckstyleExtension> {
                this.toolVersion = checkstyleVersion
                this.configFile = File(project.rootDir, CONFIG_FILE)
            },
        )

        // Spring 检查模块必须在 checkstyle 工具 classpath 上。
        // 注意要排除它自带的 checkstyle 传递依赖：spring-javaformat-checkstyle 会拖入一个
        // 较老的 checkstyle，与下面显式声明的版本冲突。不排除的话只能靠版本冲突解析
        // 「碰巧」选到高版本；显式排除后，checkstyle 版本完全由 version catalog 决定。
        val springCheckstyle = project.dependencies.create(
            "$SPRING_CHECKSTYLE_GROUP:$SPRING_CHECKSTYLE_NAME:$springJavaformatVersion",
        ) as ExternalModuleDependency
        springCheckstyle.exclude(
            mapOf(
                "group" to CHECKSTYLE_GROUP,
                "module" to CHECKSTYLE_NAME,
            ),
        )
        project.dependencies.add(CHECKSTYLE_CONFIGURATION, springCheckstyle)
        project.dependencies.add(
            CHECKSTYLE_CONFIGURATION,
            "$CHECKSTYLE_GROUP:$CHECKSTYLE_NAME:$checkstyleVersion",
        )

        // 测试代码不检查
        project.tasks.withType(Checkstyle::class.java)
            .matching { it.name == CHECKSTYLE_TEST_TASK }
            .configureEach(
                Action<Checkstyle> {
                    this.enabled = false
                },
            )

        // 把违规明细打到控制台，方便直接看到哪一行
        project.tasks.withType(Checkstyle::class.java).configureEach(
            Action<Checkstyle> {
                this.reports.xml.required.set(true)
                this.reports.html.required.set(true)
                this.logging.captureStandardError(LogLevel.INFO)
                this.logging.captureStandardOutput(LogLevel.INFO)
            },
        )

        // 聚合任务：跑 checkstyleMain（以及 spring-javaformat 的 checkFormat，如果存在）
        project.tasks.register(
            CHECKSTYLE_TASK,
            Action<Task> {
                this.group = VERIFICATION_GROUP
                this.description = "Runs checkstyle checks for this module"
                this.dependsOn(CHECKSTYLE_MAIN_TASK)
                this.dependsOn(
                    project.provider {
                        project.tasks.findByName(CHECK_FORMAT_TASK)?.let(::listOf) ?: emptyList<Any>()
                    },
                )
            },
        )
    }

    private companion object {
        /** 版本目录名。 */
        const val LIBRARY_CATALOG = "libs"

        /** 配置单路径（相对仓库根目录）。 */
        const val CONFIG_FILE = "src/checkstyle/checkstyle.xml"

        /** Gradle 上承载 checkstyle 工具及其插件的 configuration。 */
        const val CHECKSTYLE_CONFIGURATION = "checkstyle"

        const val SPRING_CHECKSTYLE_GROUP = "io.spring.javaformat"
        const val SPRING_CHECKSTYLE_NAME = "spring-javaformat-checkstyle"

        const val CHECKSTYLE_GROUP = "com.puppycrawl.tools"
        const val CHECKSTYLE_NAME = "checkstyle"

        const val CHECKSTYLE_TASK = "checkstyle"
        const val CHECKSTYLE_MAIN_TASK = "checkstyleMain"
        const val CHECKSTYLE_TEST_TASK = "checkstyleTest"
        const val CHECK_FORMAT_TASK = "checkFormat"
        const val VERIFICATION_GROUP = "verification"
    }
}
