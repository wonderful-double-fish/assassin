plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.maven.publish.plugin)
    implementation(libs.spring.boot.plugin)
    implementation(libs.dependency.management.plugin)
}

gradlePlugin {
    plugins {
        create("deploy") {
            id = "assassin.deploy"
            implementationClass = "io.github.wonderfuldoublefish.assassin.buildlogic.DeployPlugin"
            displayName = "Assassin deploy plugin"
            description = "Publish Assassin modules to Maven Central (vanniktech + signing + POM metadata)"
        }
    }
}

kotlin {
    jvmToolchain(21)
}
