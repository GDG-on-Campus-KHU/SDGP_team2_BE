package com.gdg.coffee.domain.ground.exception;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum CoffeeGroundErrorCode implements ErrorResponse {
    GROUND_NOT_FOUND   (HttpStatus.NOT_FOUND,      "GROUND_NOT_FOUND",    "해당 커피 찌꺼기를 찾을 수 없습니다."),
    GROUND_FORBIDDEN   (HttpStatus.FORBIDDEN,      "GROUND_FORBIDDEN",    "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CoffeeGroundErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code       = code;
        this.message    = message;
    }

    @Override
    public HttpStatus getHttpStatus() { return httpStatus; }

    @Override
    public String getCode() { return code; }

    @Override
    public String getMessage() { return message; }
}
