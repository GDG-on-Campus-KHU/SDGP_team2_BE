package com.gdg.coffee.domain.member.exception;

import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.type.ErrorResponse;

public class MemberException extends BaseException {
    public MemberException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
