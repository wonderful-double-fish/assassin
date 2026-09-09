plugins {
    `java-platform`
}

description = "assassin bom"

// BOM：以 java-platform 形式集中管理 assassin 系列组件坐标，
// 供业务方以 Maven 方式导入版本，无需逐个声明版本
dependencies {
    constraints {
        api(project(":assassin-core"))
        api(project(":autoconfigure:assassin-web-autoconfigure"))
        api(project(":starter:assassin-web-spring-boot-starter"))
    }
}
