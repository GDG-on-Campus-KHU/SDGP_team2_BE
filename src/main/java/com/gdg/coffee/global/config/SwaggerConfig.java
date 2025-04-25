package com.gdg.coffee.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final String version = "1.0.0";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", securityScheme())) // ⬅️ SecurityScheme 등록
                .addSecurityItem(new SecurityRequirement().addList("bearer-key")) // ⬅️ 전역 SecurityRequirement 추가
                .info(swaggerInfo());
    }

    private Info swaggerInfo() {
        return new Info()
                .version("v" + version)
                .title("EcoBEAN")
                .description("EcoBEAN 서버 API 문서입니다.");
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP) // HTTP 인증 방식
                .scheme("bearer")               // 인증 방식: bearer
                .bearerFormat("JWT")            // bearer 형식: JWT
                .in(SecurityScheme.In.HEADER)   // 인증 위치: Header
                .name("Authorization");         // 헤더 이름
    }
}

