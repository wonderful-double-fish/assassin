import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("io.spring.dependency-management")
}

extensions.configure<JavaPluginExtension> {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

extensions.configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion()}")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


private fun springBootVersion(): String =
    org.springframework.boot.gradle.plugin.SpringBootPlugin::class.java.`package`.implementationVersion
        ?: error("无法从 spring-boot-gradle-plugin 读取版本号，请检查 buildSrc/build.gradle.kts 的依赖声明")