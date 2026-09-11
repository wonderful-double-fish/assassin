plugins {
    `kotlin-dsl`
}



dependencies {
    implementation(libs.maven.publish.plugin)

}

kotlin {
    jvmToolchain(21)
}
