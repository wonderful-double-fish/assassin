plugins {
    `java-library`
}

description = "assassin web autoconfigure"

dependencies {
    api(project(":assassin-core"))

    // 以下依赖由使用方（web starter / 应用）提供，此处仅编译期引用，避免强制传递；
    // 版本由根构建导入的 spring-boot-dependencies BOM 统一管理
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.jspecify:jspecify")
    compileOnly("org.springframework:spring-webmvc")
    compileOnly("org.springframework:spring-web")
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.slf4j:slf4j-api")

    // Jackson 3（tools.jackson）：Boot 4 默认 JSON 引擎；JsonMapperBuilderCustomizer 为官方定制 SPI。
    // JavaTime 序列化器已并入 jackson-databind（tools.jackson.databind.ext.javatime），无需 jsr310 扩展模块；
    // 注解类仍保留在 Jackson 2.x 坐标（com.fasterxml.jackson.core:jackson-annotations）下，供 JsonInclude 使用
    compileOnly("org.springframework.boot:spring-boot-jackson")
    compileOnly("tools.jackson.core:jackson-databind")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")

    // 为 @ConfigurationProperties 生成 spring-configuration-metadata.json
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-web")

    // Gradle 9 需要显式声明 JUnit Platform launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
