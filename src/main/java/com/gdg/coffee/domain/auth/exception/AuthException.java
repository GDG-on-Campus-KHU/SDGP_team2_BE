package com.gdg.coffee.domain.auth.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class AuthException extends BaseException {
    public AuthException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
