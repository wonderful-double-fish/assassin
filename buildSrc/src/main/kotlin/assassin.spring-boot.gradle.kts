import org.gradle.api.tasks.bundling.Jar

/**
 * 【角色插件】Assassin 可引用的 Spring 组件库（自动装配 / starter / 公共 Spring 模块）。
 *
 * = `assassin.java` + `assassin.publish` + `org.springframework.boot`。
 * 用于内部会用到 Spring（自动配置、条件装配、`@ConfigurationProperties` 等）**且要被别人引用**的库：
 * assassin-web-autoconfigure、assassin-web-spring-boot-starter 等。
 *
 * 因为它是个「库」而不是「应用」，要把 Spring Boot 插件的「应用预设」翻过来：
 *  - 关掉 `bootJar`（没有 main class，产物也不该是胖包）、`bootRun`
 *  - 恢复普通 `jar`：去掉 `-plain` 后缀并启用，让它作为唯一的库产物（能正常发布）
 * 需要能启动的程序请用 `assassin.app`。
 *
 * 使用方式：`plugins { id("assassin.spring-boot") }`
 */
plugins {
    id("assassin.java")
    id("assassin.publish")
    id("org.springframework.boot")
}

tasks.named<Jar>("jar") {
    enabled = true
    archiveClassifier.set("")
}

tasks.named("bootJar") {
    enabled = false
}

tasks.named("bootRun") {
    enabled = false
}

