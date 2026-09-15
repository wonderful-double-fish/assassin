package io.github.wonderfuldoublefish.assassin.example.model;

import java.time.LocalDateTime;
import org.jspecify.annotations.Nullable;

/**
 * 示例用户模型.
 *
 * <p>用于演示 assassin-web 的序列化扩展：{@code Long} 主键会序列化为字符串、
 * 时间类型按配置格式输出、null 字段会被忽略。
 *
 * @param id        用户主键
 * @param name      用户昵称
 * @param createdAt 创建时间
 * @param email     邮箱，可为空
 */
public record User(Long id, String name, LocalDateTime createdAt, @Nullable String email) {
}
