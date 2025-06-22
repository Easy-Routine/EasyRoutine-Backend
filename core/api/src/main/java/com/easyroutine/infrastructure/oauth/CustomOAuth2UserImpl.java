package com.easyroutine.infrastructure.oauth;

import com.easyroutine.domain.member.Member;
import com.easyroutine.global.exception.BusinessException;
import com.easyroutine.global.response.ResultType;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2UserImpl implements CustomOAuth2User {

	private final Member member;

	public CustomOAuth2UserImpl(Member member) {
		this.member = member;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(this.member::getRole);
	}

	@Override
	public String getMemberId() {
		if (StringUtils.isEmpty(member.getId())) {
			throw new BusinessException(ResultType.MEMBER_NOT_FOUND, member.getNickname() + "의 ID가 비어있습니다.");
		}
		return this.member.getId();
	}

	@Override
	public String getName() {
		return this.member.getNickname();
	}

}
