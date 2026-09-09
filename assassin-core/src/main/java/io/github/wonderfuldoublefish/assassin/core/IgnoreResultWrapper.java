package io.github.wonderfuldoublefish.assassin.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记注解：标注后跳过统一响应体包装。
 *
 * <p>当某个控制器方法（或整个控制器）需要原样返回、不应被 {@link Result} 包裹时，
 * 在方法或类型上标注该注解即可，优先级高于 {@code Result} 包裹器。</p>
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResultWrapper {
}
