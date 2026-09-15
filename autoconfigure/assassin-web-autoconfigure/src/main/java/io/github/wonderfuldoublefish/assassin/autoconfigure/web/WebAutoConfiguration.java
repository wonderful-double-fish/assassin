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

package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * assassin-web 自动装配入口.
 *
 * <p>
 * 在 Servlet Web 环境下启用以下能力（均可通过 {@code assassin.web.*} 配置启停）：
 * <ul>
 * <li>统一响应体包装：{@link ResultResponseBodyAdvice}；</li>
 * <li>全局异常处理：{@link GlobalExceptionHandler}；</li>
 * <li>请求日志与 TraceId 透传：{@link TraceIdFilter}；</li>
 * <li>Jackson 序列化扩展：{@link JacksonCustomizer}。</li>
 * </ul>
 *
 * <p>
 * 该配置类通过
 * {@code META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports}
 * 文件注册，随 assassin-web-spring-boot-starter 一同提供。
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {

	/**
	 * 装配统一响应体包装器.
	 * @return 统一响应体包装器
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "assassin.web.response-wrapper", name = "enabled", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnClass(name = "org.springframework.http.converter.json.JacksonJsonHttpMessageConverter")
	ResultResponseBodyAdvice resultResponseBodyAdvice() {
		return new ResultResponseBodyAdvice();
	}

	/**
	 * 装配全局异常处理器.
	 * @return 全局异常处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "assassin.web.exception-handler", name = "enabled", havingValue = "true",
			matchIfMissing = true)
	GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}

	/**
	 * 装配 TraceId 过滤器.
	 * @param properties assassin-web 配置
	 * @return traceId 过滤器
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "assassin.web.trace-id", name = "enabled", havingValue = "true",
			matchIfMissing = true)
	TraceIdFilter traceIdFilter(WebProperties properties) {
		return new TraceIdFilter(properties.getTraceId().getHeader());
	}

	/**
	 * 装配 Jackson 序列化扩展.
	 * @param properties assassin-web 配置
	 * @return jackson 序列化扩展
	 */
	@Bean
	@ConditionalOnProperty(prefix = "assassin.web.jackson", name = "enabled", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnClass(name = { "tools.jackson.databind.ObjectMapper",
			"org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer" })
	JacksonCustomizer jacksonCustomizer(WebProperties properties) {
		return new JacksonCustomizer(properties.getJackson());
	}

}
