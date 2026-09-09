package io.github.wonderfuldoublefish.assassin.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * assassin-example 启动入口。
 *
 * <p>用于本地验证 assassin-web-spring-boot-starter 的统一响应体、全局异常处理、
 * TraceId 透传与 Jackson 序列化等自动装配能力。</p>
 */
@SpringBootApplication
public class AssassinExampleApplication {

    /**
     * 启动方法。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(AssassinExampleApplication.class, args);
    }
}
