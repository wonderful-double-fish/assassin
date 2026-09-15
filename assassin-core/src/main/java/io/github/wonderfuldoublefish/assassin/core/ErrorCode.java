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
 * 错误码契约.
 *
 * <p>
 * 业务错误码统一实现该接口，使异常信息能够被自动装配的全局异常处理器识别并返回给调用方。
 */
public interface ErrorCode {

	/**
	 * 错误码数值.
	 * @return 错误码
	 */
	int getCode();

	/**
	 * 错误码默认提示信息.
	 * @return 提示信息
	 */
	String getMessage();

}
