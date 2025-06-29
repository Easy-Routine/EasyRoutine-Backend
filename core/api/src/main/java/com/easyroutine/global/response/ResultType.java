package com.easyroutine.global.response;

import lombok.Getter;

@Getter
public enum ResultType {

	SUCCESS(true, "OK", "성공"),
	FAIL(false, "ERR-S001", "실패"),
	ILLEGAL_ARGUMENT(false, "ERR-B001", "잘못된 인자입니다."),
	S3_UPLOAD_FAIL(false, "ERR-B002", "S3 업로드에 실패했습니다."),
	DATA_NOT_FOUND(false, "ERR-D001", "데이터를 찾을 수 없습니다."),
	MEMBER_NOT_FOUND(false, "ERR-B003", "사용자 정보를 찾을 수 없습니다."),
	INPUT_ERROR(false, "ERR-C001", "입력값이 올바르지 않습니다."),

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