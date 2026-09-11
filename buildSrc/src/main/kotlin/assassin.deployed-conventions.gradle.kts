import org.gradle.api.tasks.bundling.Jar

/**
 * Assassin 可部署（Spring Boot 应用）模块约定插件。
 *
 * = 基础 Java 约定 + Spring Boot 插件 + 常用测试依赖。
 * 只负责能跑起来，不作为依赖库发布（需要发布时模块里自行追加
 * `id("assassin.publish-conventions")`）。
 *
 * 使用方式：`plugins { id("assassin.deployed-conventions") }`
 */
plugins {
    id("assassin.java-conventions")
    id("org.springframework.boot")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// 可部署模块最终产物是 bootJar，去掉同名普通 jar 以免 publish/bootJar 互相覆盖
tasks.named<Jar>("jar") {
    archiveClassifier.set("plain")
}
