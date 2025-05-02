package com.gdg.coffee.domain.pickup.exception;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum PickupErrorCode implements ErrorResponse {
    PICKUP_NOT_FOUND(HttpStatus.NOT_FOUND, "PICKUP400", "픽업 요청을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "PICKUP401", "요청에 대한 작업자가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PickupErrorCode(HttpStatus httpStatus, String code, String message) {
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
