plugins {
    `java-library`
}

description = "assassin web spring boot starter"

// starter 为聚合模块：引入自动装配并连带 Spring Boot Web 依赖，让使用方开箱即用。
// 无自有源码；按 starter 命名规范以 -spring-boot-starter 结尾
dependencies {
    api(project(":autoconfigure:assassin-web-autoconfigure"))
    api("org.springframework.boot:spring-boot-starter-web")
}
