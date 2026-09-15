import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing
import org.gradle.plugins.signing.SigningExtension


plugins {
    `maven-publish`
    signing
}

apply(plugin = "com.vanniktech.maven.publish")

extensions.configure<SigningExtension> {
    useGpgCmd()
}

val projectProviders = providers
val projectRef = project

extensions.configure<MavenPublishBaseExtension>("mavenPublishing") {
    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set(projectRef.name)
        description.set(projectRef.description ?: projectRef.name)
        url.set("https://github.com/wonderful-double-fish/assassin")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set(projectProviders.gradleProperty("pom.developerId").orNull ?: "wonderful-double-fish")
                name.set(projectProviders.gradleProperty("pom.developerName").orNull ?: "wonderful-double-fish")
                email.set(projectProviders.gradleProperty("pom.developerEmail").orNull ?: "cjd0655@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/wonderful-double-fish/assassin")
            connection.set("scm:git:https://github.com/wonderful-double-fish/assassin.git")
            developerConnection.set("scm:git:ssh://git@github.com/wonderful-double-fish/assassin.git")
        }
    }
}
