package com.gdg.coffee.global.common.response.bean;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum BeanErrorCode implements ErrorResponse {
    BEAN_NOT_FOUND    (HttpStatus.NOT_FOUND,    "BEAN_NOT_FOUND",    "존재하지 않는 원두입니다."),
    BEAN_FORBIDDEN    (HttpStatus.FORBIDDEN,    "BEAN_FORBIDDEN",    "원두 작업 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String     code;
    private final String     message;

    BeanErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code       = code;
        this.message    = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
