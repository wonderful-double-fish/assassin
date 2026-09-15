package io.github.wonderfuldoublefish.assassin.buildlogic

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension


class DeployPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val projectProviders = project.providers
        val projectRef = project

        project.pluginManager.apply("maven-publish")
        project.pluginManager.apply("signing")
        project.pluginManager.apply("com.vanniktech.maven.publish")

        project.extensions.configure(SigningExtension::class.java) {
            useGpgCmd()
        }

        project.extensions.configure(MavenPublishBaseExtension::class.java) {
            publishToMavenCentral()
            signAllPublications()

            pom {
                name.set(projectRef.name)
                description.set(projectRef.description ?: projectRef.name)
                url.set(PROJECT_URL)

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set(projectProviders.gradleProperty("pom.developerId").orNull ?: DEFAULT_DEVELOPER)
                        name.set(projectProviders.gradleProperty("pom.developerName").orNull ?: DEFAULT_DEVELOPER)
                        email.set(projectProviders.gradleProperty("pom.developerEmail").orNull ?: DEFAULT_EMAIL)
                    }
                }

                scm {
                    url.set(PROJECT_URL)
                    connection.set("scm:git:$PROJECT_URL.git")
                    developerConnection.set("scm:git:ssh://git@github.com/wonderful-double-fish/assassin.git")
                }
            }
        }
    }

    private companion object {
        const val PROJECT_URL = "https://github.com/wonderful-double-fish/assassin"
        const val DEFAULT_DEVELOPER = "wonderful-double-fish"
        const val DEFAULT_EMAIL = "cjd0655@gmail.com"
    }
}
