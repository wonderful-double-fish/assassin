pluginManagement {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        maven("https://maven.aliyun.com/repository/public")
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "assassin"

include("assassin-bom")
include("assassin-core")
include("autoconfigure:assassin-web-autoconfigure")
include("starter:assassin-web-spring-boot-starter")
include("assassin-example")
