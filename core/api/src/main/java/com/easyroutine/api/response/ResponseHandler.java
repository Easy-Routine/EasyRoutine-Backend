package com.easyroutine.api.response;

import com.easyroutine.global.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.easyroutine.api.controller")
public class ResponseHandler implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return (MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType)
				|| StringHttpMessageConverter.class.isAssignableFrom(converterType))
				&& !ApiResponse.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		ApiResponse<?> apiResponse = ApiResponse.success(body);

		if (body instanceof String) {
			return getString2ApiResponse(response, apiResponse);
		}

		return apiResponse;
	}

	private String getString2ApiResponse(ServerHttpResponse response, ApiResponse<?> apiResponse) {
		ObjectMapper objectMapper = new ObjectMapper();
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		try {
			return objectMapper.writeValueAsString(apiResponse);
		}
		catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Failed to convert String to JSON", e);
		}
	}

}
