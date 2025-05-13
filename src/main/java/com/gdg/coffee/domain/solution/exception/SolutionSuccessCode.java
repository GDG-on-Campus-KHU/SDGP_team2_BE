package com.gdg.coffee.domain.solution.exception;

import com.gdg.coffee.global.common.type.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SolutionSuccessCode implements SuccessResponse {
    GENERATED(HttpStatus.OK, "SOLUTION200", "업사이클링 솔루션이 성공적으로 생성되었습니다."),
    GET_DETAILS(HttpStatus.OK, "SOLUTION201", "업사이클링 솔루션 조회를 성공했습니다."),
    CUSTOM_RECOMMENDATION(HttpStatus.OK, "SOLUTION202", "사용자 조건에 따른 맞춤형 솔루션을 성공적으로 생성하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

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
