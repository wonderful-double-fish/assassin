package io.github.wonderfuldoublefish.assassin.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * assassin-example 端到端冒烟测试。
 *
 * <p>验证 assassin-web starter 自动装配效果：统一响应体包装、TraceId 响应头、
 * Jackson 序列化（Long→字符串、时间格式、忽略 null）以及全局异常处理。</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
class AssassinExampleApplicationTests {

    /** MockMvc 客户端 */
    @Autowired
    private MockMvc mockMvc;

    /**
     * 用户列表应被统一包装为 Result，且展示 TraceId 头与序列化扩展效果。
     */
    @Test
    void usersShouldBeWrappedByResult() throws Exception {
        this.mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Trace-Id"))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("成功"))
                .andExpect(jsonPath("$.data[0].id").value("1234567890123456789"))
                .andExpect(jsonPath("$.data[0].createdAt").value("2026-09-09 12:30:00"))
                .andExpect(jsonPath("$.data[0].email").doesNotExist());
    }

    /**
     * 业务异常应被全局异常处理器捕获并返回错误结构。
     */
    @Test
    void businessExceptionShouldBeHandled() throws Exception {
        this.mockMvc.perform(get("/users/error").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("参数非法：用户标识不能为空"));
    }
}
