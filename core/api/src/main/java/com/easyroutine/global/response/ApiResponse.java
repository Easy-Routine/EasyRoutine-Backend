package com.easyroutine.global.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private final boolean success;

	private final String code;

	private final T result;

	private ApiResponse(ResultType result, T resultData) {
		this.success = result.isSuccess();
		this.code = result.getCode();
		this.result = resultData;
	}

	public static <T> ApiResponse<T> success(T resultData) {
		return new ApiResponse<>(ResultType.SUCCESS, resultData);
	}

	public static <T> ApiResponse<T> fail(ResultType errorResult) {
		return new ApiResponse<>(errorResult, null);
	}

	public static <String> ApiResponse<String> fail(ResultType errorResult, String message) {
		return new ApiResponse<>(errorResult, message);
	}

}
