pluginManagement {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        maven("https://maven.aliyun.com/repository/public")
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
        google()
    }
}


rootProject.name = "assassin"

include("assassin-bom")
include("assassin-core")
include("autoconfigure:assassin-web-autoconfigure")
include("starter:assassin-web-spring-boot-starter")
include("assassin-example")

gradle.settingsEvaluated {
    println("Settings gradle.settingsEvaluated file parsed: ${settings.rootDir.name}")
}

gradle.projectsLoaded {
    println("Settings gradle.projectsLoaded all Project loaded, sub project count: ${rootProject.childProjects.size}")
}