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
 * assassin-core 核心公共包.
 *
 * <p>
 * 提供统一响应体（{@code Result}）、错误码、业务异常与包装跳过标记等与 Web 框架解耦的通用模型， 不依赖任何 Spring 组件，供上层
 * autoconfigure / starter 与应用共同复用。
 *
 * <p>
 * 本包启用 JSpecify 空安全标记：默认按非空对待，允许为空的元素一律显式标注 {@code @Nullable}。
 */
@NullMarked
package io.github.wonderfuldoublefish.assassin.core;

import org.jspecify.annotations.NullMarked;
