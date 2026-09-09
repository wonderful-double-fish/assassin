package io.github.wonderfuldoublefish.assassin.core;

import org.jspecify.annotations.Nullable;

/**
 * 统一响应体。
 *
 * <p>作为所有 API 出参的标准结构（code/message/data），由自动装配的响应包装器统一包裹，
 * 保证不同接口的返回格式一致，便于前端与网关统一处理。</p>
 *
 * @param <T> 业务数据类型
 */
public class Result<T> {

    /** 响应码，见 {@link CommonErrorCode} */
    private final int code;

    /** 响应提示信息 */
    private final String message;

    /** 业务数据，允许为空（如失败响应或空成功响应） */
    @Nullable
    private final T data;

    /**
     * 私有构造器，统一通过静态工厂创建。
     *
     * @param code    响应码
     * @param message 响应提示信息
     * @param data    业务数据，允许为空
     */
    private Result(int code, String message, @Nullable T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构建成功响应（无数据）。
     *
     * @param <T> 业务数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(CommonErrorCode.SUCCESS.getCode(), CommonErrorCode.SUCCESS.getMessage(), null);
    }

    /**
     * 构建成功响应（携带数据）。
     *
     * @param data 业务数据，允许为空
     * @param <T>  业务数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success(@Nullable T data) {
        return new Result<>(CommonErrorCode.SUCCESS.getCode(), CommonErrorCode.SUCCESS.getMessage(), data);
    }

    /**
     * 构建成功响应（携带数据与自定义提示）。
     *
     * @param data    业务数据，允许为空
     * @param message 自定义提示信息
     * @param <T>     业务数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success(@Nullable T data, String message) {
        return new Result<>(CommonErrorCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 依据错误码构建失败响应。
     *
     * @param errorCode 错误码，不能为空
     * @param <T>       业务数据类型
     * @return 失败响应
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 依据错误码与自定义提示构建失败响应。
     *
     * @param errorCode 错误码，不能为空
     * @param message   自定义提示信息
     * @param <T>       业务数据类型
     * @return 失败响应
     */
    public static <T> Result<T> fail(ErrorCode errorCode, String message) {
        return new Result<>(errorCode.getCode(), message, null);
    }

    /**
     * 以原始码值构建失败响应。
     *
     * @param code    响应码
     * @param message 提示信息
     * @param <T>     业务数据类型
     * @return 失败响应
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 是否成功。
     *
     * @return 响应码为 0 时返回 true
     */
    public boolean isSuccess() {
        return this.code == CommonErrorCode.SUCCESS.getCode();
    }

    /**
     * 获取响应码。
     *
     * @return 响应码
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 获取提示信息。
     *
     * @return 提示信息
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 获取业务数据。
     *
     * @return 业务数据，可能为空
     */
    @Nullable
    public T getData() {
        return this.data;
    }
}
