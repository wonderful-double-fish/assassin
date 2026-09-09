package io.github.wonderfuldoublefish.assassin.autoconfigure.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * WebAutoConfiguration 条件装配测试。
 *
 * <p>验证默认全量装配，以及通过 {@code assassin.web.*} 关闭后相应 Bean 不再注册。</p>
 */
class WebAutoConfigurationTests {

    /** 上下文运行器，模拟 Servlet Web 环境 */
    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(WebAutoConfiguration.class));

    /**
     * 默认配置下所有能力 Bean 均应注册。
     */
    @Test
    void defaultShouldRegisterAllBeans() {
        this.contextRunner.run(context -> assertThat(context)
                .hasSingleBean(WebProperties.class)
                .hasSingleBean(ResultResponseBodyAdvice.class)
                .hasSingleBean(GlobalExceptionHandler.class)
                .hasSingleBean(TraceIdFilter.class)
                .hasSingleBean(JacksonCustomizer.class));
    }

    /**
     * 关闭各功能后对应 Bean 不再注册。
     */
    @Test
    void disabledFeaturesShouldNotRegisterBeans() {
        this.contextRunner
                .withPropertyValues("assassin.web.response-wrapper.enabled=false",
                        "assassin.web.exception-handler.enabled=false",
                        "assassin.web.trace-id.enabled=false",
                        "assassin.web.jackson.enabled=false")
                .run(context -> assertThat(context)
                        .doesNotHaveBean(ResultResponseBodyAdvice.class)
                        .doesNotHaveBean(GlobalExceptionHandler.class)
                        .doesNotHaveBean(TraceIdFilter.class)
                        .doesNotHaveBean(JacksonCustomizer.class));
    }
}
