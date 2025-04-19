package com.easyroutine.domain.member;

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
}
