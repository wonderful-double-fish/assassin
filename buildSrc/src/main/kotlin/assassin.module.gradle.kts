import io.github.wonderfuldoublefish.assassin.buildlogic.AssassinBuildInfo
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test


plugins {
    `java-library`
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    id("io.spring.javaformat")
}

apply(plugin = "assassin.checkstyle")

extensions.configure<JavaPluginExtension> {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

extensions.configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${AssassinBuildInfo.springBootVersion}")
    }
}

configurations.getByName("testImplementation").dependencies.add(
    project.dependencies.create("org.springframework.boot:spring-boot-starter-test"),
)
configurations.getByName("testRuntimeOnly").dependencies.add(
    project.dependencies.create("org.junit.platform:junit-platform-launcher"),
)

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    enabled = true
    archiveClassifier.set("")
}

tasks.named("bootJar") {
    enabled = false
}

tasks.named("bootRun") {
    enabled = false
}
