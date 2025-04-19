package com.easyroutine.domain.member;

import com.easyroutine.api.security.oauth.response.OAuth2Response;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import static lombok.AccessLevel.*;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(generator = "uuid2")
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private String provider;
    private String providerId;
    private String email;
    private String masking_email;
    private String nickname;
    private String bio;
    private String profileImage;
    private String role;

    @Builder
    public Member(String id, String provider, String providerId, String email, String masking_email, String nickname, String bio, String profileImage, String role) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.masking_email = masking_email;
        this.nickname = nickname;
        this.bio = bio;
        this.profileImage = profileImage;
        this.role = role;
    }

    public static Member of(OAuth2Response oAuth2Response, String role) {
        return Member.builder()
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

    public static Member of(String memberId, String role) {
        return Member.builder()
                .id(memberId)
                .role(role)
                .build();
    }

    private static String getMaskEmailBy(String email) {
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        String maskedUsername = username.substring(0, 3) + "****";
        return maskedUsername + domain;
    }
}
