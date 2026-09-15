plugins {
    id("assassin.module")
    id("assassin.deploy")
}

description = "assassin core"

dependencies {
    compileOnly("org.jspecify:jspecify")
}
