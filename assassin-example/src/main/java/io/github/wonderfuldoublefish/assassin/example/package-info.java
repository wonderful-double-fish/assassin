/**
 * assassin-example 启动与应用包.
 *
 * <p>作为 assassin-web-spring-boot-starter 的演示工程，仅依赖 starter 即可获得统一响应体、
 * 全局异常处理、TraceId 透传与 Jackson 序列化扩展等能力。
 *
 * <p>本包启用 JSpecify 空安全标记：默认按非空对待，允许为空的元素一律显式标注 {@code @Nullable}。
 */
@NullMarked
package io.github.wonderfuldoublefish.assassin.example;

import org.jspecify.annotations.NullMarked;