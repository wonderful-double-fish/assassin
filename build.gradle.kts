import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.quality.CheckstyleExtension

plugins {
    // 声明但不在此应用，供各子模块按需使用
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.dependency.management) apply false
}

// 根工程不做任何编译，仅承担各子模块的公共配置
allprojects {
    group = "io.github.wonderful-double-fish"
    version = "0.0.1-SNAPSHOT"

    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }
}

subprojects {
    // 对任意应用了 java 插件的子模块统一：版本管理（Spring Boot BOM）、JDK 21 toolchain、checkstyle
    plugins.withId("java") {
        pluginManager.apply("io.spring.dependency-management")
        extensions.configure<DependencyManagementExtension> {
            imports {
                // 第三方依赖版本统一由 Spring Boot BOM 管控，业务侧只需声明坐标、无需写版本
                mavenBom("org.springframework.boot:spring-boot-dependencies:${libs.versions.spring.boot.get()}")
            }
        }

        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        }

        pluginManager.apply("checkstyle")

        extensions.configure<CheckstyleExtension> {
            // checkstyle 规则统一收敛到根工程 config/checkstyle/checkstyle.xml
            configDirectory.set(rootProject.file("config/checkstyle"))
            toolVersion = libs.versions.checkstyle.get()
        }

        // 未使用 spring-boot 插件的 java 模块（core/autoconfigure/bom/starter）同样启用 JUnit 5
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}
