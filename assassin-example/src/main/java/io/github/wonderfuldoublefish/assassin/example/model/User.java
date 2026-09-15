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

package io.github.wonderfuldoublefish.assassin.example.model;

import java.time.LocalDateTime;
import org.jspecify.annotations.Nullable;

/**
 * 示例用户模型.
 *
 * <p>
 * 用于演示 assassin-web 的序列化扩展：{@code Long} 主键会序列化为字符串、 时间类型按配置格式输出、null 字段会被忽略。
 *
 * @param id 用户主键
 * @param name 用户昵称
 * @param createdAt 创建时间
 * @param email 邮箱，可为空
 */
public record User(Long id, String name, LocalDateTime createdAt, @Nullable String email) {
}
