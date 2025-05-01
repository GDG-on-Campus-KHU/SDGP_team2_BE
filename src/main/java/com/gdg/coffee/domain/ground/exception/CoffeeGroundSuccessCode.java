package com.gdg.coffee.domain.ground.exception;

import com.gdg.coffee.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum CoffeeGroundSuccessCode implements SuccessResponse {
    GROUND_CREATE_SUCCESS(HttpStatus.CREATED,  "GROUND_CREATE_SUCCESS",  "커피 찌꺼기가 성공적으로 생성되었습니다."),
    GROUND_GET_SUCCESS   (HttpStatus.OK,      "GROUND_GET_SUCCESS",     "커피 찌꺼기 정보를 성공적으로 조회했습니다."),
    GROUND_LIST_SUCCESS  (HttpStatus.OK,      "GROUND_LIST_SUCCESS",    "커피 찌꺼기 목록을 성공적으로 조회했습니다."),
    GROUND_UPDATE_SUCCESS(HttpStatus.OK,      "GROUND_UPDATE_SUCCESS",  "커피 찌꺼기 정보가 성공적으로 수정되었습니다."),
    GROUND_DELETE_SUCCESS(HttpStatus.OK,      "GROUND_DELETE_SUCCESS",  "커피 찌꺼기가 성공적으로 삭제되었습니다."),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CoffeeGroundSuccessCode(HttpStatus httpStatus, String code, String message) {
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
