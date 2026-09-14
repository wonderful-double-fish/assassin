/**
 * buildSrc —— 本项目的「构建逻辑中心」。
 *
 * 这里集中管理所有构建期插件与依赖（版本统一来自 ../gradle/libs.versions.toml），
 * 业务模块的 build.gradle.kts 只需要一行 `id("assassin.xxx")`，
 * 不必再关心 Spring Boot / 依赖管理 / 发布插件怎么写。
 *
 * ══ 角色插件（模块按「你是什么模块」选其中一个）══════════════════════
 *  - assassin.bom            BOM 版本约束清单（java-platform）      = publish
 *  - assassin.library        可复用 Java 库（不引用 Spring）         = java + publish
 *  - assassin.spring-boot    可引用的 Spring 组件库（自动装配/starter）= java + publish + boot
 *  - assassin.app            可部署 Spring Boot 应用                = java + boot
 *
 * ══ 底层零件（由上面几个组合，也可单独用于特殊模块）══════════════════
 *  - assassin.java       java-library + JDK21 + UTF-8 + Spring Boot BOM + JUnit Platform
 *  - assassin.publish    Maven Central 发布（vanniktech）
 *  - assassin.root       根项目专用（承载 group / version 单一来源，不套 java）
 *
 * ══ 命名规则 ══════════════════════════════════════════════════════
 * 文件名 = 插件 id（去掉 .gradle.kts）。`assassin.*.gradle.kts` 放在
 * src/main/kotlin 根目录，id 就是 `assassin.*`；若放进子目录（如 java/），
 * id 会变成 `java.assassin.*`，所以约定插件一律平铺在根目录。
 */
plugins {
    `kotlin-dsl`
}

dependencies {
    // 供 assassin.publish 使用
    implementation(libs.maven.publish.plugin)
    // 供 assassin.spring-boot / assassin.app 使用
    implementation(libs.spring.boot.plugin)
    // 供 assassin.java 使用
    implementation(libs.dependency.management.plugin)
}

kotlin {
    jvmToolchain(21)
}
