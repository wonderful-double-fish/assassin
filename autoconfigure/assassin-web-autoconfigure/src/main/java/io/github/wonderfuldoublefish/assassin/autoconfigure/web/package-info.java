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

/**
 * assassin-web 自动装配包.
 *
 * <p>
 * 基于 {@code spring-boot-autoconfigure} 提供 Web 通用能力的自动装配：统一响应体包装、 全局异常处理、TraceId
 * 请求日志过滤器，以及 Jackson 序列化扩展，均可通过 {@code assassin.web.*} 配置启停。
 *
 * <p>
 * 本包启用 JSpecify 空安全标记：默认按非空对待，允许为空的元素一律显式标注 {@code @Nullable}。
 */
@NullMarked
package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import org.jspecify.annotations.NullMarked;
