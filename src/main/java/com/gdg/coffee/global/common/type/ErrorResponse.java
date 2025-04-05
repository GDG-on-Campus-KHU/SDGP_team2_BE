package com.gdg.coffee.global.common.type;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
