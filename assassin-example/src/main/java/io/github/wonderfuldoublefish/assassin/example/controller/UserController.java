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

package io.github.wonderfuldoublefish.assassin.example.controller;

import io.github.wonderfuldoublefish.assassin.core.BusinessException;
import io.github.wonderfuldoublefish.assassin.core.CommonErrorCode;
import io.github.wonderfuldoublefish.assassin.example.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例用户接口.
 *
 * <p>
 * 用于演示 assassin-web 自动装配效果：{@code list()} 返回的普通 POJO 会被包装为统一响应体， {@code error()}
 * 抛出的业务异常会被全局异常处理器捕获。
 */
@RestController
@RequestMapping("/users")
public class UserController {

	/**
	 * 查询用户列表.
	 * @return 用户列表（将由统一响应体包装器包装为 Result）
	 */
	@GetMapping
	public List<User> list() {
		return List.of(new User(1234567890123456789L, "Alice", LocalDateTime.of(2026, 9, 9, 12, 30, 0), null));
	}

	/**
	 * 触发业务异常的示例端点.
	 *
	 * <p>
	 * 该接口必然失败，用于验证全局异常处理器的返回结构。
	 */
	@GetMapping("/error")
	public void error() {
		throw new BusinessException(CommonErrorCode.BAD_REQUEST, "参数非法：用户标识不能为空");
	}

}
