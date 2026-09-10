import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.quality.CheckstyleExtension

plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.dependency.management) apply false
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
