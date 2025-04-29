package com.gdg.coffee.domain.bean.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class BeanException extends BaseException {
    public BeanException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
