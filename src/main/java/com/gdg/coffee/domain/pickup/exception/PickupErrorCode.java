package com.gdg.coffee.domain.pickup.exception;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum PickupErrorCode implements ErrorResponse {
    PICKUP_NOT_FOUND(HttpStatus.NOT_FOUND, "PICKUP404", "픽업 요청을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "PICKUP401", "요청에 대한 작업자가 아닙니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "PICKUP400", "유효하지 않은 입력값입니다."),
    ALREADY_COMPLETED(HttpStatus.CONFLICT, "PICKUP410", "이미 완료된 픽업 요청입니다."),
    CANNOT_CANCEL(HttpStatus.BAD_REQUEST, "PICKUP412", "현재 상태에서 픽업 요청을 취소할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PICKUP500", "서버 내부 오류가 발생했습니다.");

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
