package com.gdg.coffee.domain.member.exception;

import com.gdg.coffee.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessResponse {

    INFO_SUCCESS(HttpStatus.OK,"MEMBER_INFO_SUCCESS","회원정보 조회에 성공했습니다."),
    REPORT_SUCCESS(HttpStatus.OK, "MEMBER_REPORT_SUCCESS", "환경 기여도 조회가 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberSuccessCode(HttpStatus httpStatus, String code, String message) {
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
