plugins {
    `kotlin-dsl`
}

group = "io.github.wonderful-double-fish"
version = "0.0.1-SNAPSHOT"


dependencies {
    // 供 assassin.publish-conventions 应用 com.vanniktech.maven.publish
    // 必须是 implementation：compileOnly 在子项目运行期会 NoClassDefFoundError
    implementation("com.vanniktech:gradle-maven-publish-plugin:0.37.0")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}