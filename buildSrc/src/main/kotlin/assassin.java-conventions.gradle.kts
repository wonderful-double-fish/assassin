import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.tasks.testing.Test

/**
 * 通用 Java 约定插件：
 *  - 应用 java-library / checkstyle
 *  - 统一 Java 工具链版本
 *  - 通过 Gradle 原生 platform() 引入 Spring Boot BOM 做版本对齐（不依赖 io.spring.dependency-management）
 *  - 使用仓库根目录的 checkstyle 规则
 *  - 测试统一走 JUnit Platform
 *
 * 使用方式：plugins { id("assassin.java-conventions") }
 *
 * 注意：本插件刻意不依赖任何外部 Gradle 插件，避免污染主构建的脚本 classpath。
 */
plugins {
    `java-library`
    checkstyle
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AssassinVersions.JAVA))
    }
}

dependencies {
    val springBootBom = platform("org.springframework.boot:spring-boot-dependencies:${AssassinVersions.SPRING_BOOT}")
    // implementation 覆盖 compile/runtime/test 所有派生配置；注解处理器配置相互独立，需单独引入
    listOf("implementation", "annotationProcessor", "testAnnotationProcessor").forEach { configurationName ->
        add(configurationName, springBootBom)
    }
}

extensions.configure<CheckstyleExtension> {
    configDirectory.set(rootProject.file("config/checkstyle"))
    toolVersion = AssassinVersions.CHECKSTYLE
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
