/**
 * 【角色插件】Assassin 根项目（root project）约定插件。
 *
 * 根项目不产出任何东西，功能只有一处：本项目的 group / version 是「单一来源」，
 * 集中写在 `gradle.properties`（`group=` / `version=`），Gradle 会自动应用到
 * 所有项目，因此这里**不需要** `allprojects {}` / `subprojects {}` 做跨项目配置 ——
 * 这也是 Gradle 官方明确推荐的写法（cross-project configuration 会引入配置期耦合，
 * 阻碍 configuration-on-demand 等优化）。
 *
 * 各子模块的 group / version 由它们各自引入的 `assassin.library` /
 * `assassin.spring-boot` / `assassin.app` / `assassin.bom` 继承，无需重复声明。
 *
 * 这里刻意不 extends `assassin.java`：根项目没有源码，不需要 Java 工具链等配置。
 *
 * 使用方式：根 `build.gradle.kts` 里 `plugins { id("assassin.root") }`
 */
