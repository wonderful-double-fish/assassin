import org.gradle.api.tasks.bundling.Jar


plugins {
    id("assassin.module")
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("plain")
}

tasks.named("bootJar") {
    enabled = true
}

tasks.named("bootRun") {
    enabled = true
}
