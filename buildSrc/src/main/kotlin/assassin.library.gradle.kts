/**
 * 【角色插件】Assassin 可复用 Java 库（**不**引用 Spring 的纯 Java 模块）。
 *
 * = `assassin.java` + `assassin.publish`。
 * 用于只依赖 JDK / 第三方非 Spring 库、且要被别人引用的模块，例如 assassin-core。
 * 引用 Spring 的模块请改用 `assassin.spring-boot`，那样从 build 文件就能看出模块性质。
 *
 * 使用方式：`plugins { id("assassin.library") }`
 */
plugins {
    id("assassin.java")
    id("assassin.publish")
}
