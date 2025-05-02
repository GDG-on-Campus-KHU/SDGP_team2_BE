package com.gdg.coffee.domain.pickup.exception;

import com.gdg.coffee.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum PickupSuccessCode implements SuccessResponse {
    PICKUP_SUCCESS_CODE(HttpStatus.OK,"PICKUP_INFO_SUCCESS","수거 요청 조회에 성공했습니다."),
    PICKUP_GET_LIST_SUCCESS (HttpStatus.OK,      "PICKUP_GET_LIST_SUCCESS",  "수거 요청 목록을 성공적으로 조회했습니다."),
    PICKUP_STATUS_UPDATE_SUCCESS   (HttpStatus.OK,      "PICKUP_STATUS_UPDATE_SUCCESS",    "수거 요청 상태가 성공적으로 수정되었습니다."),
    PICKUP_DELETE_SUCCESS (HttpStatus.OK,      "PICKUP_DELETE_SUCCESS", "수거 요청이 성공적으로 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PickupSuccessCode(HttpStatus httpStatus, String code, String message) {
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
