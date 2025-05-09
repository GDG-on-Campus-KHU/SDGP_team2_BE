package com.gdg.coffee.domain.cafe.exception;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum CafeErrorCode implements ErrorResponse {
    CAFE_NOT_FOUND      (HttpStatus.NOT_FOUND,    "CAFE_NOT_FOUND",      "존재하지 않는 카페입니다."),
    CAFE_INVALID_INPUT  (HttpStatus.BAD_REQUEST,  "CAFE_INVALID_INPUT",  "잘못된 카페 요청입니다."),
    CAFE_FORBIDDEN      (HttpStatus.FORBIDDEN,    "CAFE_FORBIDDEN",      "이 카페에 대한 권한이 없습니다."),
    CAFE_DUPLICATE      (HttpStatus.FORBIDDEN,    "CAFE_DUPLICATE",      "이미 카페가 존재합니다."),
    CAFE_NOT_FOUND_BY_MEMBER(HttpStatus.NOT_FOUND, "CAFE_NOT_FOUND_BY_MEMBER", "해당 회원의 카페가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CafeErrorCode(HttpStatus httpStatus, String code, String message) {
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
