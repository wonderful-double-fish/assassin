plugins {
    id("assassin.deployed-conventions")
}

description = "assassin example"

dependencies {
    implementation(project(":starter:assassin-web-spring-boot-starter"))

    compileOnly("org.jspecify:jspecify")

    testImplementation("org.springframework.boot:spring-boot-webmvc-test")
}
