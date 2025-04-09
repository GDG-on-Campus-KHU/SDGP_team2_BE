package com.gdg.coffee.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Swagger 연습용 Board API Document")
                .version("v0.0.1")
                .description("Swagger 연습용 게시판 API 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
