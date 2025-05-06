package com.easyroutine.api.controller;

import com.easyroutine.api.security.jwt.JsonWebTokenUtil;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "컨텍스트 API", description = "Context 관련 API")
public class ContextController {

    private final MemberService memberService;
    private final JsonWebTokenUtil jwtUtil;

    @Operation(summary = "Context", description = "JWT가 유효한지 확인합니다.")
    @GetMapping("/context")
    public String context() {
        return "Context is working!";
    }

    @Operation(summary = "Access Token 발급", description = "Access Token을 발급합니다. (테스트 계정을 사용합니다.)")
    @GetMapping("/get/tester")
    public String getTester() {
        Member tester = memberService.getTester();
        return jwtUtil.createAccessToken(tester.getId(), tester.getRole(), 60*60*60L);
    }
}
