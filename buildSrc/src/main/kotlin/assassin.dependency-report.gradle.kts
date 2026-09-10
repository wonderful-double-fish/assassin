import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier

/**
 * 依赖分析报告插件，注册两个任务：
 *  - dependencyReport         导出所有可解析配置的依赖清单（Maven 坐标）到 build/reports/dependencies
 *  - compileDependencyReport  仅打印 compileClasspath 的依赖坐标
 *
 * 使用方式：plugins { id("assassin.dependency-report") }
 */
val reportProjectPath = project.path
val reportProjectName = project.name
val reportConfigurations = configurations

/** 把配置解析结果转换成 Maven 坐标（项目依赖输出为 project :path，排除自身）。 */
fun collectCoordinates(configuration: Configuration, selfPath: String): List<String> =
    configuration.incoming.resolutionResult.allComponents
        .map { it.id }
        .filterNot { it is ProjectComponentIdentifier && it.projectPath == selfPath }
        .mapNotNull { id ->
            when (id) {
                is ModuleComponentIdentifier -> "${id.group}:${id.module}:${id.version}"
                is ProjectComponentIdentifier -> "project ${id.projectPath}"
                else -> null
            }
        }
        .distinct()
        .sorted()

tasks.register("dependencyReport") {
    group = "assassin"
    description = "导出所有可解析配置的依赖清单（Maven 坐标）到 build/reports/dependencies"
    val outputDir = layout.buildDirectory.dir("reports/dependencies")
    doLast {
        val dir = outputDir.get().asFile
        dir.deleteRecursively()
        dir.mkdirs()

        val resolvable = reportConfigurations
            .filter { it.isCanBeResolved && it.allDependencies.isNotEmpty() }
            .sortedBy { it.name }

        val summary = StringBuilder()
        summary.appendLine("依赖清单: $reportProjectPath ($reportProjectName)")
        summary.appendLine("=".repeat(60))

        resolvable.forEach { configuration ->
            val coordinates = runCatching { collectCoordinates(configuration, reportProjectPath) }
                .getOrElse { listOf("<无法解析: ${it.message}>") }

            File(dir, "${configuration.name}.txt").writeText(
                buildString {
                    appendLine("# $reportProjectPath:$configuration.name")
                    coordinates.forEach { appendLine("- $it") }
                },
            )

            summary.appendLine()
            summary.appendLine("[${configuration.name}] 共 ${coordinates.size} 个")
            coordinates.forEach { summary.appendLine("  - $it") }
        }

        val summaryFile = File(dir, "dependencies.txt")
        summaryFile.writeText(summary.toString())
        logger.lifecycle("📦 [dependencyReport] $reportProjectPath 依赖清单已写入: $summaryFile")
    }
}

tasks.register("compileDependencyReport") {
    group = "assassin"
    description = "打印 compileClasspath 的依赖 Maven 坐标"
    doLast {
        val configuration = reportConfigurations.findByName("compileClasspath")
        if (configuration == null) {
            logger.lifecycle("📦 [compileDependencyReport] $reportProjectPath 未应用 java 插件，跳过")
            return@doLast
        }
        val coordinates = runCatching { collectCoordinates(configuration, reportProjectPath) }
            .getOrElse { listOf("<无法解析: ${it.message}>") }

        logger.lifecycle("📦 [compileDependencyReport] $reportProjectPath:compileClasspath 共 ${coordinates.size} 个依赖")
        coordinates.forEach { logger.lifecycle("  - $it") }
    }
}
