package com.gdg.coffee.global.common.response.bean;

import com.gdg.coffee.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum BeanSuccessCode implements SuccessResponse {
    BEAN_CREATE_SUCCESS (HttpStatus.CREATED, "BEAN_CREATE_SUCCESS", "원두가 성공적으로 생성되었습니다."),
    BEAN_LIST_SUCCESS   (HttpStatus.OK,      "BEAN_LIST_SUCCESS",   "원두 목록을 성공적으로 조회했습니다."),
    BEAN_UPDATE_SUCCESS (HttpStatus.OK,      "BEAN_UPDATE_SUCCESS", "원두 정보가 성공적으로 수정되었습니다."),
    BEAN_DELETE_SUCCESS (HttpStatus.OK,      "BEAN_DELETE_SUCCESS", "원두가 성공적으로 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String     code;
    private final String     message;

    BeanSuccessCode(HttpStatus httpStatus, String code, String message) {
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
