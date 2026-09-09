package io.github.wonderfuldoublefish.assassin.core;

/**
 * 错误码契约。
 *
 * <p>业务错误码统一实现该接口，使异常信息能够被自动装配的全局异常处理器识别并返回给调用方。</p>
 */
public interface ErrorCode {

    /**
     * 错误码数值。
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 错误码默认提示信息。
     *
     * @return 提示信息
     */
    String getMessage();
}
