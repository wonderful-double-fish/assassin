import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`java-library`
import org.gradle.kotlin.dsl.named


plugins {
    `java-library`
    id("org.springframework.boot")
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

