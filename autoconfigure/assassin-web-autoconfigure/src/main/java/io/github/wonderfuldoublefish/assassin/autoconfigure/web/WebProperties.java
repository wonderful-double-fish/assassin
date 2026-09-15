package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * assassin-web 自动装配配置项.
 *
 * <p>配置前缀为 {@code assassin.web}，各功能默认开启；可通过 application.yaml 灵活启停：
 *
 * <pre>
 * assassin:
 *   web:
 *     response-wrapper:
 *       enabled: true
 *     exception-handler:
 *       enabled: true
 *     trace-id:
 *       enabled: true
 *       header: X-Trace-Id
 *     jackson:
 *       enabled: true
 *       date-time-format: yyyy-MM-dd HH:mm:ss
 *       long-to-string: true
 *       omit-null: true
 * </pre>
 */
@ConfigurationProperties(prefix = "assassin.web")
public class WebProperties {

  /** 统一响应体包装配置. */
  private final ResponseWrapper responseWrapper = new ResponseWrapper();

  /** 全局异常处理配置. */
  private final ExceptionHandler exceptionHandler = new ExceptionHandler();

  /** 请求日志与 TraceId 配置. */
  private final TraceId traceId = new TraceId();

  /** Jackson 序列化扩展配置. */
  private final Jackson jackson = new Jackson();

  /**
   * 获取统一响应体包装配置.
   *
   * @return 统一响应体包装配置
   */
  public ResponseWrapper getResponseWrapper() {
    return this.responseWrapper;
  }

  /**
   * 获取全局异常处理配置.
   *
   * @return 全局异常处理配置
   */
  public ExceptionHandler getExceptionHandler() {
    return this.exceptionHandler;
  }

  /**
   * 获取请求日志与 TraceId 配置.
   *
   * @return TraceId 配置
   */
  public TraceId getTraceId() {
    return this.traceId;
  }

  /**
   * 获取 Jackson 序列化扩展配置.
   *
   * @return Jackson 配置
   */
  public Jackson getJackson() {
    return this.jackson;
  }

  /**
   * 统一响应体包装配置.
   */
  public static class ResponseWrapper {

    /** 是否启用统一响应体包装. */
    private boolean enabled = true;

    /**
     * 是否启用.
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
      return this.enabled;
    }

    /**
     * 设置是否启用.
     *
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }
  }

  /**
   * 全局异常处理配置.
   */
  public static class ExceptionHandler {

    /** 是否启用全局异常处理. */
    private boolean enabled = true;

    /**
     * 是否启用.
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
      return this.enabled;
    }

    /**
     * 设置是否启用.
     *
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }
  }

  /**
   * 请求日志与 TraceId 配置.
   */
  public static class TraceId {

    /** 是否启用请求日志与 TraceId 透传. */
    private boolean enabled = true;

    /** TraceId 请求头名称. */
    private String header = "X-Trace-Id";

    /**
     * 是否启用.
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
      return this.enabled;
    }

    /**
     * 设置是否启用.
     *
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    /**
     * 获取 TraceId 请求头名称.
     *
     * @return TraceId 请求头名称
     */
    public String getHeader() {
      return this.header;
    }

    /**
     * 设置 TraceId 请求头名称.
     *
     * @param header TraceId 请求头名称
     */
    public void setHeader(String header) {
      this.header = header;
    }
  }

  /**
   * Jackson 序列化扩展配置.
   */
  public static class Jackson {

    /** 是否启用 assassin 提供的 Jackson 序列化扩展. */
    private boolean enabled = true;

    /** 时间类型（LocalDateTime）序列化格式. */
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    /** 是否将 Long/long 序列化为字符串，避免前端 JS 精度丢失. */
    private boolean longToString = true;

    /** 是否忽略值为 null 的字段. */
    private boolean omitNull = true;

    /**
     * 是否启用.
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
      return this.enabled;
    }

    /**
     * 设置是否启用.
     *
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    /**
     * 获取时间序列化格式.
     *
     * @return 时间序列化格式
     */
    public String getDateTimeFormat() {
      return this.dateTimeFormat;
    }

    /**
     * 设置时间序列化格式.
     *
     * @param dateTimeFormat 时间序列化格式
     */
    public void setDateTimeFormat(String dateTimeFormat) {
      this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * 是否将 Long 序列化为字符串.
     *
     * @return 是否将 Long 序列化为字符串
     */
    public boolean isLongToString() {
      return this.longToString;
    }

    /**
     * 设置是否将 Long 序列化为字符串.
     *
     * @param longToString 是否将 Long 序列化为字符串
     */
    public void setLongToString(boolean longToString) {
      this.longToString = longToString;
    }

    /**
     * 是否忽略 null 字段.
     *
     * @return 是否忽略 null 字段
     */
    public boolean isOmitNull() {
      return this.omitNull;
    }

    /**
     * 设置是否忽略 null 字段.
     *
     * @param omitNull 是否忽略 null 字段
     */
    public void setOmitNull(boolean omitNull) {
      this.omitNull = omitNull;
    }
  }
}
