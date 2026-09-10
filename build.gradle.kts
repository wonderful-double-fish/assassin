import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.quality.CheckstyleExtension

plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.dependency.management) apply false
    `maven-publish`
}

allprojects {
    group = "io.github.wonderful-double-fish"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    plugins.withId("java") {
        pluginManager.apply("io.spring.dependency-management")
        extensions.configure<DependencyManagementExtension> {
            imports {
                mavenBom("org.springframework.boot:spring-boot-dependencies:${libs.versions.spring.boot.get()}")
            }
        }

        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        }

        pluginManager.apply("checkstyle")

        extensions.configure<CheckstyleExtension> {
            configDirectory.set(rootProject.file("config/checkstyle"))
            toolVersion = libs.versions.checkstyle.get()
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }

    // 当子模块应用了 maven-publish 插件时自动统一应用以下发布通用配置
    plugins.withId("maven-publish") {
        extensions.configure<PublishingExtension> {
            publications {
                create<MavenPublication>("mavenJava") {
                    // groupId 和 version 默认会自动集成全局 allprojects 里的设置
                    // artifactId 默认会自动使用各自子模块的项目名（例如 assassin-core）
                    from(components["java"])
                }
            }
            // mavenLocal() 为 publishToMavenLocal 默认配置，无需额外在 repositories 中重复声明
            repositories {
                mavenLocal()
            }

            // 仅在任务完成时，控制台输出一行轻量的成功通知
            tasks.withType<PublishToMavenLocal>().configureEach {
                doLast {
                    val repoUrl = project.extensions.getByType<PublishingExtension>().repositories.mavenLocal().url
                    val groupPath = project.group.toString().replace('.', '/')
                    val targetDir = File(repoUrl).resolve("$groupPath/${project.name}/${project.version}")

                    logger.lifecycle("🚀 [MavenLocal] 模块 ${project.name} 已发布至: $targetDir")
                }
            }
        }
    }
}

// 可以对build进行前置后置操作
tasks.register<Task>("literalAssassin") {
    description = "Project Assassin build.gradle task"
    group = "assassin"
    doFirst {
        println("Assassin is running...")
    }
    doLast {
        println("Assassin is done.")
    }
}
