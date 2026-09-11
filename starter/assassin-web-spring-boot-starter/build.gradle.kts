plugins {
    `java-library`
    id("assassin.publish-conventions")
}

description = "assassin web spring boot starter"

dependencies {
    api(project(":autoconfigure:assassin-web-autoconfigure"))
    api("org.springframework.boot:spring-boot-starter-web")
}
