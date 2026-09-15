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

package io.github.wonderfuldoublefish.assassin.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * assassin-example 启动入口.
 *
 * <p>
 * 用于本地验证 assassin-web-spring-boot-starter 的统一响应体、全局异常处理、 TraceId 透传与 Jackson 序列化等自动装配能力。
 */
@SpringBootApplication
public class AssassinExampleApplication {

	/**
	 * 启动方法.
	 * @param args 启动参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(AssassinExampleApplication.class, args);
	}

}
