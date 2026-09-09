package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import io.github.wonderfuldoublefish.assassin.core.BusinessException;
import io.github.wonderfuldoublefish.assassin.core.CommonErrorCode;
import io.github.wonderfuldoublefish.assassin.core.ErrorCode;
import io.github.wonderfuldoublefish.assassin.core.Result;

import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 *
 * <p>统一兜底控制器抛出的异常：业务异常（{@link BusinessException}）按错误码返回，
 * 未捕获异常记录日志并返回系统内部错误，保证出参结构始终为 {@link Result}。</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 日志对象 */
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常。
     *
     * <p>错误码为合法 HTTP 错误码（400~599）时同步设置响应状态码；自定义业务码
     * 仅体现在响应体中，HTTP 状态保持 200。</p>
     *
     * @param ex       业务异常
     * @param response 当前响应，用于设置错误状态码
     * @return 统一失败响应
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException ex, HttpServletResponse response) {
        ErrorCode errorCode = ex.getErrorCode();
        applyErrorStatus(response, errorCode.getCode());
        log.warn("业务异常, code={}, message={}", errorCode.getCode(), ex.getMessage());
        return Result.fail(errorCode, ex.getMessage());
    }

    /**
     * 兜底处理未捕获异常。
     *
     * @param ex 未捕获异常
     * @return 统一失败响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception ex) {
        log.error("系统未捕获异常", ex);
        return Result.fail(CommonErrorCode.INTERNAL_ERROR);
    }

    private void applyErrorStatus(HttpServletResponse response, int code) {
        HttpStatus status = HttpStatus.resolve(code);
        if (status != null && status.isError()) {
            response.setStatus(status.value());
        }
    }
}
