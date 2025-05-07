package com.easyroutine.infrastructure.oauth;

import com.easyroutine.domain.member.Member;
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
        return this.member.getId();
    }

    @Override
    public String getName() {
        return this.member.getNickname();
    }
}
