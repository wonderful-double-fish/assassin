plugins {
    id("assassin.bom-conventions")
}

description = "assassin bom"

dependencies {
    constraints {
        api(project(":assassin-core"))
        api(project(":autoconfigure:assassin-web-autoconfigure"))
        api(project(":starter:assassin-web-spring-boot-starter"))
    }
}
