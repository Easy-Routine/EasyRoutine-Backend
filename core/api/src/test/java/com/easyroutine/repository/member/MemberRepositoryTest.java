package com.easyroutine.repository.member;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.domain.member.MemberStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class MemberRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("provider와 providerId로 멤버를 조회한다. (provider + providerId 조합이 유일함을 기대한다.)")
	@Test
	void findByProviderAndProviderId() {
		// given
		Member memberA = getMember("google", "1234", "memberA");
		Member memberB = getMember("kakao", "5678", "memberB");
		Member memberC = getMember("naver", "1234", "memberC");

		memberRepository.saveAllAndFlush(List.of(memberA, memberB, memberC));

		// when
		Member foundMember = memberRepository.findByProviderAndProviderId("google", "1234").orElseThrow();

		// then
		assertEquals("memberA", foundMember.getNickname());
		assertEquals("google", foundMember.getProvider());
		assertEquals("1234", foundMember.getProviderId());
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