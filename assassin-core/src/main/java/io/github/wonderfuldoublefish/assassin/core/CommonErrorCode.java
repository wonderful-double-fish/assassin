/*
 * Copyright 2024-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wonderfuldoublefish.assassin.core;

/**
 * 通用错误码.
 *
 * <p>
 * 覆盖了最常见的请求校验与系统错误场景，业务方如无特殊需求可直接复用；
 *
 * <p>
 * 如需自定义错误码，实现 {@link ErrorCode} 接口并提供枚举即可。
 */
public enum CommonErrorCode implements ErrorCode {

	/** 成功. */
	SUCCESS(0, "成功"),

	/** 请求参数不合法. */
	BAD_REQUEST(400, "请求参数错误"),

	/** 未认证或登录已失效. */
	UNAUTHORIZED(401, "未认证或登录已失效"),

	/** 已认证但无访问权限. */
	FORBIDDEN(403, "无访问权限"),

	/** 资源不存在. */
	NOT_FOUND(404, "资源不存在"),

	/** 资源冲突. */
	CONFLICT(409, "资源冲突"),

	/** 请求体格式错误. */
	UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),

	/** 服务器内部错误. */
	INTERNAL_ERROR(500, "系统内部错误"),

	/** 服务暂不可用. */
	SERVICE_UNAVAILABLE(503, "服务暂不可用");

	/** 错误码数值. */
	private final int code;

	/** 错误码提示信息. */
	private final String message;

	/**
	 * 构造器.
	 * @param code 错误码数值
	 * @param message 错误码提示信息
	 */
	CommonErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
