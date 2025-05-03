package com.easyroutine.api.exception;

import com.easyroutine.global.exception.BusinessException;
import com.easyroutine.global.exception.DataException;
import com.easyroutine.global.response.ApiResponse;
import com.easyroutine.global.response.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<String> handleBusinessException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage(), e);
        return ApiResponse.fail(e.getResultType());
    }

    @ExceptionHandler(DataException.class)
    public ApiResponse<String> handleDataException(DataException e) {
        log.error("DataException: {}", e.getMessage(), e);
        return ApiResponse.fail(e.getResultType());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException : {}", e.getMessage(), e);
        return ApiResponse.fail(ResultType.INPUT_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ApiResponse.fail(ResultType.FAIL);
    }
}
