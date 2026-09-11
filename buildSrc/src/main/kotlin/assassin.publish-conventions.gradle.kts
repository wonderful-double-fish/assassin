import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.plugins.signing.SigningExtension

/**
 * Maven Central 发布约定插件（基于 com.vanniktech.maven.publish）。
 *  - 自动生成 sources / javadoc jar（java-platform 为空的对应 jar）
 *  - 版本号以 -SNAPSHOT 结尾时自动发布到 Central 的 snapshot 仓库，且 SNAPSHOT 不会被签名
 *  - POM 元数据统一在插件内维护
 *
 * 凭据（不写进代码，由外部注入）：
 *  - mavenCentralUsername / mavenCentralPassword      Central Portal 的 user token
 *  - 签名二选一（仅正式版需要，SNAPSHOT 会跳过签名）：
 *      内存私钥：signingInMemoryKey / signingInMemoryKeyId / signingInMemoryKeyPassword
 *      本机 gpg-agent：signing.gnupg.keyName / signing.gnupg.passphrase / signing.gnupg.executable
 *
 * 使用方式：plugins { id("assassin.publish-conventions") }
 */
plugins {
    `maven-publish`
    signing
}

apply(plugin = "com.vanniktech.maven.publish")

// 未提供内存私钥时改走本机 gpg（读 signing.gnupg.* 属性），避免 daemon 报 "No configured signatory"
if (!providers.gradleProperty("signingInMemoryKey").isPresent) {
    configure<SigningExtension> {
        useGpgCmd()
    }
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
