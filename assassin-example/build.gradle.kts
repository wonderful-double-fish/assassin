plugins {
    id("assassin.service")
}

description = "assassin example"

dependencies {
    implementation(project(":starter:assassin-web-spring-boot-starter"))

    compileOnly("org.jspecify:jspecify")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.springframework.boot:spring-boot-webmvc-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}
