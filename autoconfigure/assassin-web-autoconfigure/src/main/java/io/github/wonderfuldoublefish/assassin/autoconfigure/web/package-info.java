/**
 * assassin-web 自动装配包.
 *
 * <p>基于 {@code spring-boot-autoconfigure} 提供 Web 通用能力的自动装配：统一响应体包装、
 * 全局异常处理、TraceId 请求日志过滤器，以及 Jackson 序列化扩展，均可通过
 * {@code assassin.web.*} 配置启停。
 *
 * <p>本包启用 JSpecify 空安全标记：默认按非空对待，允许为空的元素一律显式标注 {@code @Nullable}。
 */
@NullMarked
package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import org.jspecify.annotations.NullMarked;