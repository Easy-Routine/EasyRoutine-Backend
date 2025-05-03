package com.easyroutine.global.response;

import lombok.Getter;

@Getter
public enum ResultType {
    SUCCESS(true, "OK", "성공"),
    FAIL(false, "ERR-S001" ,"실패"),
    DATA_NOT_FOUND(false, "ERR-D001" ,"실패"),
    ILLEGAL_ARGUMENT(false, "ERR-B001" ,"실패"),
    S3_UPLOAD_FAIL(false, "ERR-B002" ,"실패"),
    INPUT_ERROR(false, "ERR-C001", "실패")
    ;

    private final boolean success;
    private final String code;
    private final String message;

    ResultType(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}