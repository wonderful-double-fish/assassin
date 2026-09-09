package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import io.github.wonderfuldoublefish.assassin.core.IgnoreResultWrapper;
import io.github.wonderfuldoublefish.assassin.core.Result;

import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应体包装器。
 *
 * <p>拦截所有 JSON 出参，将普通 POJO 包装为 {@link Result}，使接口返回格式统一。
 * 以下场景不会被包装：</p>
 * <ul>
 *     <li>标注了 {@link IgnoreResultWrapper} 的类或方法；</li>
 *     <li>返回类型本身是 {@link Result} 或 {@link HttpEntity}（如 {@code ResponseEntity}）；</li>
 *     <li>void、字符串、字节流、资源等非 JSON 转换器输出的内容。</li>
 * </ul>
 */
@ControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持对当前响应进行包装。
     *
     * @param returnType    控制器方法返回类型
     * @param converterType 命中的消息转换器
     * @return 需要包装时返回 true
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 仅包装由 Jackson 3（Boot 4 默认 JSON 引擎）JacksonJsonHttpMessageConverter 输出的 JSON；
        // 字符串/二进制等交由各自转换器原样输出
        if (!JacksonJsonHttpMessageConverter.class.isAssignableFrom(converterType)) {
            return false;
        }
        Class<?> rawType = returnType.getParameterType();
        return !hasIgnoreWrapper(returnType) && isWrapperEligible(rawType);
    }

    /**
     * 输出前将业务数据包装为成功响应。
     *
     * @param body                 业务数据
     * @param returnType           控制器方法返回类型
     * @param selectedContentType  已选中的内容类型
     * @param selectedConverterType 已命中的消息转换器
     * @param request              服务端请求
     * @param response             服务端响应
     * @return 包装后的 {@link Result}，恒非空
     */
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType,
            MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        return Result.success(body);
    }

    private boolean hasIgnoreWrapper(MethodParameter returnType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), IgnoreResultWrapper.class)
                || (returnType.getMethod() != null
                        && AnnotatedElementUtils.hasAnnotation(returnType.getMethod(), IgnoreResultWrapper.class));
    }

    private boolean isWrapperEligible(Class<?> rawType) {
        return !void.class.isAssignableFrom(rawType) && !Result.class.isAssignableFrom(rawType)
                && !HttpEntity.class.isAssignableFrom(rawType) && !Resource.class.isAssignableFrom(rawType)
                && !CharSequence.class.isAssignableFrom(rawType) && !byte[].class.isAssignableFrom(rawType);
    }
}
