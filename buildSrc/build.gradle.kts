plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.maven.publish.plugin)
    implementation(libs.spring.boot.plugin)
    implementation(libs.dependency.management.plugin)
    implementation(libs.checkstyle)
    implementation(libs.spring.javaformat.plugin)
    implementation(libs.spring.javaformat.checkstyle)
}

gradlePlugin {
    plugins {
        create("deploy") {
            id = "assassin.deploy"
            implementationClass = "io.github.wonderfuldoublefish.assassin.buildlogic.DeployPlugin"
            displayName = "Assassin deploy plugin"
            description = "Publish Assassin modules to Maven Central (vanniktech + signing + POM metadata)"
        }
        create("checkstyle") {
            id = "assassin.checkstyle"
            implementationClass = "io.github.wonderfuldoublefish.assassin.buildlogic.CheckstylePlugin"
            displayName = "Assassin checkstyle plugin"
            description = "Checkstyle code style checks for all Assassin modules"
        }
    }
}

kotlin {
    jvmToolchain(21)
}
