plugins {
    `java`
    alias(libs.plugins.spring.boot)
}

description = "assassin example"

dependencies {
    // 仅依赖 assassin-web starter，即可获得统一响应体、全局异常、TraceId、Jackson 等能力
    implementation(project(":starter:assassin-web-spring-boot-starter"))

    // JSpecify 空安全注解：仅编译期使用
    compileOnly("org.jspecify:jspecify")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Boot 4 中 MockMvc 测试自动装配已拆分至独立模块
    testImplementation("org.springframework.boot:spring-boot-webmvc-test")

    // Gradle 9 需要显式声明 JUnit Platform launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
