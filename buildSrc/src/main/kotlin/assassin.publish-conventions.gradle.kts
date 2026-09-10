import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal

/**
 * Maven 发布约定插件：
 *  - 自动应用 maven-publish
 *  - 生成 groupId/artifactId/version 取自工程的 mavenJava 发布物（java 或 java-platform 组件均支持）
 *  - 发布目标为 mavenLocal
 *  - publishToMavenLocal 结束时输出产物落盘路径
 *
 * 使用方式：plugins { id("assassin.publish-conventions") }
 */
plugins {
    `maven-publish`
}

val projectRef = project
val publishingExtension = extensions.getByType<PublishingExtension>()

publishingExtension.repositories {
    mavenLocal()
}

fun registerMavenJava(componentName: String) {
    if (publishingExtension.publications.findByName("mavenJava") != null) {
        return
    }
    publishingExtension.publications.create<MavenPublication>("mavenJava") {
        from(projectRef.components[componentName])
        pom {
            name.set(projectRef.name)
            description.set(projectRef.description ?: projectRef.name)
        }
    }
}

plugins.withId("java-platform") {
    registerMavenJava("javaPlatform")
}

plugins.withId("java") {
    registerMavenJava("java")
}

tasks.withType<PublishToMavenLocal>().configureEach {
    doLast {
        val repoUrl = projectRef.extensions.getByType<PublishingExtension>().repositories.mavenLocal().url
        val groupPath = projectRef.group.toString().replace('.', '/')
        val targetDir = File(repoUrl).resolve("$groupPath/${projectRef.name}/${projectRef.version}")
        logger.lifecycle("🚀 [MavenLocal] 模块 ${projectRef.name} 已发布至: $targetDir")
    }
}
