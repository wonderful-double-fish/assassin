
plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.maven.publish.plugin)
    implementation(libs.spring.boot.plugin)
    implementation(libs.dependency.management.plugin)
}

kotlin {
    jvmToolchain(21)
}
