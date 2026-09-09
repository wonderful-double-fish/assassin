plugins {
    `java-library`
}

description = "assassin core"

dependencies {
    // JSpecify 空安全注解：仅编译期需要（package-info 的 @NullMarked、@Nullable 等），不传递到运行期
    compileOnly("org.jspecify:jspecify")
}
