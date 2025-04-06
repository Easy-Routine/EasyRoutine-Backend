package com.easyroutine.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    private String username;
    private String nickname;
    private String email;
    private String profileImage;
    private String provider;
    private String providerId;
    private String role;

    @Builder
    private Member(String username, String nickname, String email, String profileImage, String provider, String providerId, String role) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }
}
