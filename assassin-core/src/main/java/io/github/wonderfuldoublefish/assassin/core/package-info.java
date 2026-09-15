/**
 * assassin-core 核心公共包.
 *
 * <p>提供统一响应体（{@code Result}）、错误码、业务异常与包装跳过标记等与 Web 框架解耦的通用模型，
 * 不依赖任何 Spring 组件，供上层 autoconfigure / starter 与应用共同复用。
 *
 * <p>本包启用 JSpecify 空安全标记：默认按非空对待，允许为空的元素一律显式标注 {@code @Nullable}。
 */
@NullMarked
package io.github.wonderfuldoublefish.assassin.core;

import org.jspecify.annotations.NullMarked;