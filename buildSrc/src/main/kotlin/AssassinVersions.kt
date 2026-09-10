/**
 * buildSrc 无法读取主工程的 version catalog（gradle/libs.versions.toml），
 * 因此约定插件使用的版本集中在这里维护，修改时请与 libs.versions.toml 保持同步。
 */
object AssassinVersions {
    const val SPRING_BOOT = "4.1.1"
    const val CHECKSTYLE = "10.21.2"
    const val JAVA = 21
}
