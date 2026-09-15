plugins {
    `java-library`
    id("assassin.deploy")
}

description = "assassin web autoconfigure"

dependencies {
    api(project(":assassin-core"))

    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.jspecify:jspecify")
    compileOnly("org.springframework:spring-webmvc")
    compileOnly("org.springframework:spring-web")
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.slf4j:slf4j-api")

    compileOnly("org.springframework.boot:spring-boot-jackson")
    compileOnly("tools.jackson.core:jackson-databind")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-web")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
