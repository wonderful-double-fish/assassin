package io.github.wonderfuldoublefish.assassin.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 构建期元信息，供 binary plugin 与 precompiled script plugin 共用。
 */
object AssassinBuildInfo {

    /**
     * 从当前构建 classpath 上已加载的 spring-boot-gradle-plugin 读取版本号。
     *
     * 这样「构建期 Spring Boot 插件版本」与「依赖管理导入的 Spring Boot BOM 版本」
     * 永远一致，版本号只需要在 buildSrc/build.gradle.kts 里声明一次。
     */
    val springBootVersion: String
        get() = org.springframework.boot.gradle.plugin.SpringBootPlugin::class.java
            .`package`
            .implementationVersion
            ?: error("无法从 spring-boot-gradle-plugin 读取版本号，请检查 buildSrc/build.gradle.kts 的依赖声明")
}
