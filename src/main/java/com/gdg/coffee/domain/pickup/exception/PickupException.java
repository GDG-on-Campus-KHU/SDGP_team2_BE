package com.gdg.coffee.domain.pickup.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class PickupException extends BaseException {
    public PickupException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
