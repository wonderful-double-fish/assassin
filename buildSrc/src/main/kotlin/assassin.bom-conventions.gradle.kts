/**
 * Assassin BOM（`java-platform` 常量约束）模块约定插件。
 *
 * = `java-platform` + Maven Central 发布约定（BOM 只是一个 platform，不参与编译）。
 * 模块内只需再声明 `constraints` 即可。
 *
 * 使用方式：`plugins { id("assassin.bom-conventions") }`
 */
plugins {
    `java-platform`
    id("assassin.publish-conventions")
}
