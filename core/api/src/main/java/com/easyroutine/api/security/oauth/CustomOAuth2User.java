package com.easyroutine.api.security.oauth;

import com.easyroutine.api.security.oauth.response.OAuth2Response;
import com.easyroutine.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final Member member;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {
        this.member = Member.builder()
                .provider(oAuth2Response.getProvider())
                .email(oAuth2Response.getEmail())
                .masking_email(getMaskEmailBy(oAuth2Response.getEmail()))
                .profileImage(oAuth2Response.getProfileImage())
                .nickname(oAuth2Response.getName())
                .provider(oAuth2Response.getProvider())
                .providerId(oAuth2Response.getProviderId())
                .role(role)
                .build();
    }

    public CustomOAuth2User(String memberId, String role) {
        this.member = Member.builder()
                .id(memberId)
                .role(role)
                .build();
    }

    public String getMaskEmailBy(String email) {
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        String maskedUsername = username.substring(0, 3) + "****";
        return maskedUsername + domain;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.member::getRole);
    }

    public Member getMember() {
        return this.member;
    }

    public String getMemberId() {
        return this.member.getId();
    }

    @Override
    public String getName() {
        return this.member.getNickname();
    }
}
