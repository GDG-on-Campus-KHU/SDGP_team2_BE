package com.gdg.coffee.domain.solution.exception;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class SolutionException extends BaseException {
    public SolutionException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
