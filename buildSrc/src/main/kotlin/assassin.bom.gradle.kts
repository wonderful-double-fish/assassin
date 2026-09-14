/**
 * 【底层零件】Assassin BOM（`java-platform` 版本约束清单）约定插件。
 *
 * = `java-platform` + `assassin.publish`。
 * 平台模块不参与编译、不产出代码，只发布一份 constraints，所以这里**不**套 `assassin.java`。
 * 模块里只需再声明 `constraints { api(project(":xxx")) }`。
 *
 * 使用方式：`plugins { id("assassin.bom") }`
 */
plugins {
    `java-platform`
    id("assassin.publish")
}
