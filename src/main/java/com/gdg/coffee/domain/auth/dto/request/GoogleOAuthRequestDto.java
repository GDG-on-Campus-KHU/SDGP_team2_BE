package com.gdg.coffee.domain.auth.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GoogleOAuthRequestDto {
    private String authorizationCode;
}
