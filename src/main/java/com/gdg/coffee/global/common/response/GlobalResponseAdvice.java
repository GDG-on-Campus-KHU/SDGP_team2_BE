package com.gdg.coffee.global.common.response;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalResponseAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException e) {
        ErrorResponse errorResponse = e.getErrorType();
        return new ResponseEntity<>(
                ApiResponse.error(errorResponse),
                e.getHttpStatus()
        );

    }

    // DTO 유효성 검사 실패 시 모두 global BAD_REQUEST로 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        ApiResponse<?> body = ApiResponse.error(ErrorCode.BAD_REQUEST);
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatus())
                .body(body);
    }

    // DB 제약조건 위반 시 모두 global BAD_REQUEST로 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDb(DataIntegrityViolationException ex) {
        ApiResponse<?> body = ApiResponse.error(ErrorCode.BAD_REQUEST);
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatus())
                .body(body);
    }
}
