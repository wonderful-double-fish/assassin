/**
 * buildSrc —— 本项目的「构建逻辑中心」。
 *
 * 这里集中管理所有构建期插件与依赖（版本统一来自 ../gradle/libs.versions.toml），
 * 业务模块的 build.gradle.kts 只需要 `id("assassin.xxx-conventions")`，
 * 不必再关心 Spring Boot / 依赖管理 / 发布插件怎么写。
 *
 * 已有的约定插件（src/main/kotlin）：
 *  - assassin.java-conventions      Java 基础：java-library + JDK21 + Spring Boot BOM + JUnit Platform
 *  - assassin.publish-conventions   Maven Central 发布（vanniktech）
 *  - assassin.service-conventions   可复用服务/组件 = java-conventions + publish-conventions
 *  - assassin.bom-conventions       BOM = java-platform + publish-conventions
 *  - assassin.deployed-conventions  可部署应用 = java-conventions + spring-boot
 */
plugins {
    `kotlin-dsl`
}

dependencies {
    // maven 发布插件（assassin.publish-conventions 使用）
    implementation(libs.maven.publish.plugin)
    // Spring Boot 插件（assassin.deployed-conventions 使用）
    implementation(libs.spring.boot.plugin)
    // Spring 依赖管理插件（assassin.java-conventions 使用）
    implementation(libs.dependency.management.plugin)
}

kotlin {
    jvmToolchain(21)
}
