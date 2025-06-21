package com.easyroutine.api.aop;

import com.easyroutine.global.exception.BusinessException;
import com.easyroutine.global.response.ResultType;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class AuthenticationCheckAspect {

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restControllerMethods() {
	}

	@Before("restControllerMethods()")
	public void checkAuthentication(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Object[] args = joinPoint.getArgs();
		Parameter[] parameters = method.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			for (Annotation annotation : parameters[i].getAnnotations()) {
				if (annotation.annotationType() == AuthenticationPrincipal.class) {
					Object arg = args[i];
					if (arg == null || !(arg instanceof CustomOAuth2User)) {
						throw new BusinessException(ResultType.MEMBER_NOT_FOUND, "인증 정보가 없습니다.");
					}
				}
			}
		}
	}

}
