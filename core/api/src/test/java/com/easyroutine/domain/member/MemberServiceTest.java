package com.easyroutine.domain.member;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 삭제한다. 회원 삭제는 soft delete로 처리한다.")
    @Test
    void deleteMember() {

        // given
        Member member = getMember("google", "1234", "tester");
        memberRepository.save(member);

        // when
        memberService.deleteMember(member.getId());

        // then
        Member deletedMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(deletedMember.getStatus()).isEqualTo(MemberStatus.DELETED);
    }


    private static Member getMember(String provider, String providerId, String nickname) {
        return Member.builder()
                .provider(provider)
                .providerId(providerId)
                .nickname(nickname)
                .email("" + nickname + "@example.com")
                .masking_email("" + nickname + "@example.com")
                .role(MemberRole.MEMBER)
                .status(MemberStatus.ACTIVE)
                .build();
    }
}