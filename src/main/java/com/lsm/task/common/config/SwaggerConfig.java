package com.lsm.task.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("페이히어 1차 과제전형 서버 API 명세서")
                            .description("페이히어 1차 과제전형 API 명세서입니다.")
                            .version("v1.0"));
    }
}
