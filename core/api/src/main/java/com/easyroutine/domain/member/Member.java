package com.easyroutine.domain.member;

import com.easyroutine.global.exception.BusinessException;
import com.easyroutine.global.response.ResultType;
import com.easyroutine.infrastructure.oauth.response.OAuth2Response;
import com.easyroutine.domain.BaseEntity;
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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(length = 50, nullable = false)
    private String provider;

    @Column(length = 255, nullable = false)
    private String providerId;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String masking_email;

    @Column(length = 255, nullable = false)
    private String nickname;

    private String bio;

    private String profileImage;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    private Member(String id, String provider, String providerId, String email, String masking_email, String nickname, String bio, String profileImage, MemberRole role, MemberStatus status) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.masking_email = masking_email;
        this.nickname = nickname;
        this.bio = bio;
        this.profileImage = profileImage;
        this.role = role;
        this.status = status;
    }

    public static Member of(OAuth2Response oAuth2Response, MemberRole role) {
        return Member.builder()
                .provider(oAuth2Response.getProvider())
                .email(oAuth2Response.getEmail())
                .masking_email(getMaskEmailBy(oAuth2Response.getEmail()))
                .profileImage(oAuth2Response.getProfileImage())
                .nickname(oAuth2Response.getName())
                .provider(oAuth2Response.getProvider())
                .providerId(oAuth2Response.getProviderId())
                .role(role)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public static Member of(String memberId, MemberRole role) {
        return Member.builder()
                .id(memberId)
                .role(role)
                .build();
    }
    public static Member of(String memberId) {
        if(memberId == null || memberId.isEmpty()) {
            throw new BusinessException(ResultType.MEMBER_NOT_FOUND, "사용자 정보가 없습니다.");
        }

        return Member.builder()
                .id(memberId)
                .build();
    }

    private static String getMaskEmailBy(String email) {
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        String maskedUsername = username.substring(0, 3) + "****";
        return maskedUsername + domain;
    }

    public void deleteMember() {
        this.status = MemberStatus.DELETED;
        this.setDeletedAt();
    }

    public String getRole() {
        return this.role.getAuthority();
    }
}
