plugins {
    `kotlin-dsl`
}

group = "io.github.wonderful-double-fish"
version = "0.0.1-SNAPSHOT"

repositories {
    maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
    google()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}