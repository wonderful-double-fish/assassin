/**
 * 根构建脚本：只保留「全局坐标」和「根项目任务」。
 *
 * 所有 Java / 依赖管理 / 发布 / Spring Boot 相关逻辑都收敛到 buildSrc 的
 * 约定插件里（见 buildSrc/src/main/kotlin 与 buildSrc/build.gradle.kts），
 * 子模块按角色引入即可：
 *
 *  - 可复用服务/组件：plugins { id("assassin.service-conventions") }
 *  - BOM：           plugins { id("assassin.bom-conventions") }
 *  - 可部署应用：     plugins { id("assassin.deployed-conventions") }
 */
allprojects {
    group = "io.github.wonderful-double-fish"
    version = "0.0.1"
}

// 可以对build进行前置后置操作
tasks.register<Task>("literalAssassin") {
    description = "Project Assassin build.gradle task"
    group = "assassin"
    doFirst {
        println("Assassin is running...")
    }
    doLast {
        println("Assassin is done.")
    }
}
