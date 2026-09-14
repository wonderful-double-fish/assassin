import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

/**
 * 【底层零件】Assassin Java 基础约定 —— 所有 Java 模块的共同底座。
 *
 * 只做「任何 Java 模块都该有」的事，不判断模块角色：
 *  - `java-library`（提供 api / implementation 依赖划分）
 *  - JDK 21 toolchain、UTF-8 编译编码
 *  - 引入 Spring Boot BOM（版本从构建期的 spring-boot-gradle-plugin 上读取，两者天然一致）
 *  - 单测统一 JUnit Platform
 *
 * 一般不用直接引入它，而是引入角色插件 `assassin.library` / `assassin.spring-boot` / `assassin.app`。
 * 使用方式：`plugins { id("assassin.java") }`
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
