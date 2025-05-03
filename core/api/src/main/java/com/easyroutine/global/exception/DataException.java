package com.easyroutine.global.exception;

import com.easyroutine.global.response.ResultType;
import lombok.Getter;

@Getter
public class DataException extends RuntimeException {
    private final ResultType resultType;
    private final String message;

    public DataException(ResultType resultType, String message) {
        this.resultType = resultType;
        this.message = message;
    }
}
