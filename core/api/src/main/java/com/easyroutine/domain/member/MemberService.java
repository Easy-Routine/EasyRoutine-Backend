package com.easyroutine.domain.member;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/members")
public class MemberService {

    @PutMapping
    public Member updateMember() {
        return new Member();
    }
}
