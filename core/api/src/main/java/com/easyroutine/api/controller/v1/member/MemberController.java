package com.easyroutine.api.controller.v1.member;

import com.easyroutine.domain.alarm.ExerciseAlarmSetting;
import com.easyroutine.domain.alarm.ExerciseAlarmSettingService;
import com.easyroutine.domain.alarm.dto.ExerciseAlarmSettingDto;
import com.easyroutine.domain.member.Member;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import com.easyroutine.domain.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API", description = "회원 관련 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final ExerciseAlarmSettingService exerciseAlarmSettingService;

	@Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
	@DeleteMapping
	public String deleteMember(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		String memberId = customOAuth2User.getMemberId();
		return memberService.deleteMember(memberId);
	}

	@Operation(summary = "알람 저장", description = "알람 저장 API")
	@PostMapping("/save/alarm")
	public Long saveAlarm(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, @RequestBody ExerciseAlarmSettingDto.ReqSaveExerciseAlarmSetting req) {
		Member member = Member.of(customOAuth2User.getMemberId());
		return exerciseAlarmSettingService.saveExerciseAlarmSetting(req, member);
	}

}
