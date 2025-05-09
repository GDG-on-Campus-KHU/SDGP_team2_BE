package com.gdg.coffee.domain.member.exception;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorResponse {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER400", "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String code, String message) {
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

