package com.easyroutine.domain.member;

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
            throw new IllegalArgumentException("Member not found");
        });
        member.changeStatus(MemberStatus.DELETED);
        return memberId;
    }
}
