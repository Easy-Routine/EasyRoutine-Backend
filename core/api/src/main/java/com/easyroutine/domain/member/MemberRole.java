package com.easyroutine.domain.member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum MemberRole implements GrantedAuthority {
    MEMBER, ADMIN,
    ;


    @Override
    public String getAuthority() {
        return name();
    }
}
