package com.gdg.coffee.global.common.response;

import com.gdg.coffee.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum SuccessCode implements SuccessResponse {
    SUCCESS(HttpStatus.OK,"SUCCESS", "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "CREATED", "새 리소스가 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
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
