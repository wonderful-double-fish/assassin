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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

/**
 * WebAutoConfiguration 条件装配测试.
 *
 * <p>
 * 验证默认全量装配，以及通过 {@code assassin.web.*} 关闭后相应 Bean 不再注册。
 */
class WebAutoConfigurationTests {

	/** 上下文运行器，模拟 Servlet Web 环境. */
	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
		.withConfiguration(AutoConfigurations.of(WebAutoConfiguration.class));

	/**
	 * 默认配置下所有能力 Bean 均应注册.
	 */
	@Test
	void defaultShouldRegisterAllBeans() {
		this.contextRunner.run(context -> assertThat(context).hasSingleBean(WebProperties.class)
			.hasSingleBean(ResultResponseBodyAdvice.class)
			.hasSingleBean(GlobalExceptionHandler.class)
			.hasSingleBean(TraceIdFilter.class)
			.hasSingleBean(JacksonCustomizer.class));
	}

	/**
	 * 关闭各功能后对应 Bean 不再注册.
	 */
	@Test
	void disabledFeaturesShouldNotRegisterBeans() {
		this.contextRunner
			.withPropertyValues("assassin.web.response-wrapper.enabled=false",
					"assassin.web.exception-handler.enabled=false", "assassin.web.trace-id.enabled=false",
					"assassin.web.jackson.enabled=false")
			.run(context -> assertThat(context).doesNotHaveBean(ResultResponseBodyAdvice.class)
				.doesNotHaveBean(GlobalExceptionHandler.class)
				.doesNotHaveBean(TraceIdFilter.class)
				.doesNotHaveBean(JacksonCustomizer.class));
	}

}
