import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

/**
 * Assassin 基础 Java 约定插件。
 *
 * 统一维护「所有 Java 模块」都必须遵守的规则：
 *  - `java-library`（提供 api / implementation 依赖划分）
 *  - JDK 21 toolchain、UTF-8 编译编码
 *  - 引入 Spring Boot BOM，版本从 buildSrc/build.gradle.kts 里声明的
 *    spring-boot-gradle-plugin 上读取，保证「构建期插件版本」与「BOM 版本」永远一致
 *  - 单测统一 JUnit Platform
 *
 * 使用方式：`plugins { id("assassin.java-conventions") }`
 */
plugins {
    `java-library`
    id("io.spring.dependency-management")
}

extensions.configure<JavaPluginExtension> {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

extensions.configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion()}")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

/** 从已加载的 Spring Boot 插件类上读取版本号，避免版本号在多个文件里重复维护。 */
private fun springBootVersion(): String =
    org.springframework.boot.gradle.plugin.SpringBootPlugin::class.java.`package`.implementationVersion
        ?: error("无法从 spring-boot-gradle-plugin 读取版本号，请检查 buildSrc/build.gradle.kts 的依赖声明")
