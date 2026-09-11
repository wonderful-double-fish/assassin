/**
 * Assassin 可复用服务/组件模块（jar 形态）约定插件。
 *
 * = 基础 Java 约定 + Maven Central 发布约定。
 * 适用于 assassin-core、autoconfigure、starter 这类「被别的项目依赖」的模块。
 *
 * 使用方式：`plugins { id("assassin.service-conventions") }`
 */
plugins {
    id("assassin.java-conventions")
    id("assassin.publish-conventions")
}
