plugins {
    `java-library`
    id("assassin.publish-conventions")
}

description = "assassin core"

dependencies {
    compileOnly("org.jspecify:jspecify")
}

