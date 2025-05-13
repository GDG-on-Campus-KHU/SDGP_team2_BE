package com.gdg.coffee.domain.solution.exception;

import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum SolutionErrorCode implements ErrorResponse {
    PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "GEMINI500", "Gemini 응답 파싱에 실패했습니다."),
    REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "GEMINI502", "Gemini API 요청에 실패했습니다."),
    EMPTY_RESPONSE(HttpStatus.NO_CONTENT, "GEMINI204", "Gemini 응답이 비어 있습니다."),
    SOLUTION_NOT_FOUND(HttpStatus.NOT_FOUND, "GEMINI404", "존재하지 않는 솔루션입니다"),
    INVALID_PROMPT(HttpStatus.BAD_REQUEST, "GEMINI400", "유효하지 않은 프롬프트 형식입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GEMINI501", "Gemini 처리 중 서버 오류가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SolutionErrorCode(HttpStatus httpStatus, String code, String message) {
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
