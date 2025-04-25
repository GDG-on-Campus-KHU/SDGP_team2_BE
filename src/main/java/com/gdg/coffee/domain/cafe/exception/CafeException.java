package com.gdg.coffee.domain.cafe.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class CafeException extends BaseException {
    public CafeException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
