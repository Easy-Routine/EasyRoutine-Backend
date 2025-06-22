package com.easyroutine.domain.member;

import com.easyroutine.global.exception.DataException;
import com.easyroutine.global.response.ResultType;
import com.easyroutine.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional(rollbackFor = Exception.class)
	public String deleteMember(String memberId) {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		Member member = optionalMember.orElseGet(() -> {
			throw new DataException(ResultType.DATA_NOT_FOUND, "Member not found : " + memberId);
		});
		member.deleteMember();
		return memberId;
	}

	public Member getTester() {
		return memberRepository.findByNickname("tester").orElseGet(() -> {
			throw new DataException(ResultType.DATA_NOT_FOUND, "Member not found : " + "tester");
		});
	}

}
