

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
