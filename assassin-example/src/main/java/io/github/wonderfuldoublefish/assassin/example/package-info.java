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
 * assassin-example 启动与应用包.
 *
 * <p>
 * 作为 assassin-web-spring-boot-starter 的演示工程，仅依赖 starter 即可获得统一响应体、 全局异常处理、TraceId 透传与
 * Jackson 序列化扩展等能力。
 *
 * <p>
 * 本包启用 JSpecify 空安全标记：默认按非空对待，允许为空的元素一律显式标注 {@code @Nullable}。
 */
@NullMarked
package io.github.wonderfuldoublefish.assassin.example;

import org.jspecify.annotations.NullMarked;
