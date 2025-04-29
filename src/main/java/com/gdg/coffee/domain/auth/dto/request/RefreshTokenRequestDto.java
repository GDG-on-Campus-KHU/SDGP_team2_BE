package com.gdg.coffee.domain.auth.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequestDto {
    private String refreshToken;
}
