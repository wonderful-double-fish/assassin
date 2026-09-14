import org.gradle.api.tasks.bundling.Jar

/**
 * 【角色插件】Assassin 可部署应用（能 `java -jar` 启动的 Spring Boot 程序）。
 *
 * = `assassin.java` + `org.springframework.boot` + 常用测试依赖。
 * 产物是可执行 `bootJar`；普通 `jar` 加 `-plain` 后缀避免两者同名冲突。
 *
 * 刻意**不含** `assassin.publish`：可部署产物一般不作为依赖发布。
 * 确实需要发布时，在模块里显式追加一行 `id("assassin.publish")`。
 *
 * 使用方式：`plugins { id("assassin.app") }`
 */
plugins {
    id("assassin.java")
    id("org.springframework.boot")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("plain")
}
