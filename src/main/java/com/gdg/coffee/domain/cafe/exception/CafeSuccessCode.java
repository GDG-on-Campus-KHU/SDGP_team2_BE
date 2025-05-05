package com.gdg.coffee.domain.cafe.exception;

import com.gdg.coffee.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum CafeSuccessCode implements SuccessResponse {
    CAFE_CREATE_SUCCESS   (HttpStatus.CREATED, "CAFE_CREATE_SUCCESS",    "카페가 성공적으로 생성되었습니다."),
    CAFE_GET_SUCCESS      (HttpStatus.OK,      "CAFE_GET_SUCCESS",       "카페 정보를 성공적으로 조회했습니다."),
    CAFE_GET_LIST_SUCCESS (HttpStatus.OK,      "CAFE_GET_LIST_SUCCESS",  "카페 목록을 성공적으로 조회했습니다."),
    CAFE_UPDATE_SUCCESS   (HttpStatus.OK,      "CAFE_UPDATE_SUCCESS",    "카페 정보가 성공적으로 수정되었습니다."),
    CAFE_EXIST_CHECK_SUCCESS(HttpStatus.OK, "CAFE_EXIST_CHECK_SUCCESS", "카페 존재 여부를 성공적으로 확인했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CafeSuccessCode(HttpStatus httpStatus, String code, String message) {
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
