package io.github.wonderfuldoublefish.assassin.core;

import org.jspecify.annotations.Nullable;

/**
 * 业务异常。
 *
 * <p>业务代码中遇到可预期的失败（如参数校验失败、资源不存在）时抛出该异常，</p>
 * <p>由全局异常处理器统一捕获并转换为 {@link Result} 返回，无需在业务方法内手写返回逻辑。</p>
 */
public class BusinessException extends RuntimeException {

    /** 异常对应的错误码 */
    private final ErrorCode errorCode;

    /**
     * 以错误码默认提示信息构造异常。
     *
     * @param errorCode 错误码，不能为空
     */
    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage(), null);
    }

    /**
     * 以错误码与自定义提示信息构造异常。
     *
     * @param errorCode 错误码，不能为空
     * @param message   自定义提示信息，可为空，为空时回退到错误码默认提示
     */
    public BusinessException(ErrorCode errorCode, @Nullable String message) {
        this(errorCode, message, null);
    }

    /**
     * 完整构造器，携带原始异常以便排查。
     *
     * @param errorCode 错误码，不能为空
     * @param message   自定义提示信息，可为空，为空时回退到错误码默认提示
     * @param cause     底层原因，可为空
     */
    public BusinessException(ErrorCode errorCode, @Nullable String message, @Nullable Throwable cause) {
        super((message == null || message.isBlank()) ? errorCode.getMessage() : message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 获取业务错误码。
     *
     * @return 错误码
     */
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
