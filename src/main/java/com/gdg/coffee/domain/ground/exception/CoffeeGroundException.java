package com.gdg.coffee.domain.ground.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class CoffeeGroundException extends BaseException {
    public CoffeeGroundException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
