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

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * 全局 Jackson（Jackson 3）序列化扩展.
 *
 * <p>
 * 通过 {@link JsonMapperBuilderCustomizer}（Boot 4 官方 SPI）参与 Spring Boot 的 JsonMapper 装配，
 * 提供以下开箱即用的能力（均可通过 {@code assassin.web.jackson.*} 配置关闭）：
 * <ul>
 * <li>{@code LocalDateTime} 按统一格式输出（默认 yyyy-MM-dd HH:mm:ss）；</li>
 * <li>Long/long 序列化为字符串，规避前端 JavaScript 精度丢失；</li>
 * <li>序列化时忽略值为 null 的字段，使报文更精简。</li>
 * </ul>
 */
public class JacksonCustomizer implements JsonMapperBuilderCustomizer {

	/**
	 * Jackson 扩展配置.
	 */
	private final WebProperties.Jackson properties;

	/**
	 * 构造器.
	 * @param properties jackson 扩展配置
	 */
	public JacksonCustomizer(WebProperties.Jackson properties) {
		this.properties = properties;
	}

	@Override
	public void customize(JsonMapper.Builder builder) {
		if (this.properties.isOmitNull()) {
			builder.changeDefaultPropertyInclusion((value) -> value.withValueInclusion(JsonInclude.Include.NON_NULL)
				.withContentInclusion(JsonInclude.Include.NON_NULL));
		}
		if (this.properties.isLongToString()) {
			SimpleModule longModule = new SimpleModule();
			longModule.addSerializer(Long.class, ToStringSerializer.instance);
			longModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
			builder.addModule(longModule);
		}
		String dateTimeFormat = this.properties.getDateTimeFormat();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
		SimpleModule dateModule = new SimpleModule();
		dateModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
		dateModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
		builder.addModule(dateModule);
	}

}
