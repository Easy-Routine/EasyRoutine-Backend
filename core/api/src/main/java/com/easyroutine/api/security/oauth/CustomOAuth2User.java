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
                .username(oAuth2Response.getProvider() + "-" + oAuth2Response.getProviderId())
                .provider(oAuth2Response.getProvider())
                .email(oAuth2Response.getEmail())
                .nickname(oAuth2Response.getName())
                .provider(oAuth2Response.getProvider())
                .providerId(oAuth2Response.getProviderId())
                .role(role)
                .build();
    }

    public CustomOAuth2User(String username, String role) {
        this.member = Member.builder()
                .username(username)
                .role(role)
                .build();
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
    public String getName() {
        return this.member.getNickname();
    }

    public String getUsername() {
        return this.member.getUsername();
    }
}
