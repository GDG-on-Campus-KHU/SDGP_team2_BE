package com.gdg.coffee.global.security.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class SecurityException extends BaseException {
    public SecurityException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
