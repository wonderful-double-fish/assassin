/**
 * 根构建脚本：只做两件事 —— 引入根项目约定插件、声明根项目自己的任务。
 *
 * group / version 写在 gradle.properties（单一来源），由各模块的约定插件继承，
 * 所以这里不再需要 allprojects {} / subprojects {} 这类跨项目配置。
 *
 * 子模块按角色选一个插件即可（完整清单见 buildSrc/build.gradle.kts）：
 *  - assassin.bom            BOM 版本约束清单
 *  - assassin.library        可复用 Java 库（不引用 Spring）
 *  - assassin.spring-boot    可引用的 Spring 组件库（自动装配 / starter）
 *  - assassin.app            可部署 Spring Boot 应用
 */
plugins {
    id("assassin.root")
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
