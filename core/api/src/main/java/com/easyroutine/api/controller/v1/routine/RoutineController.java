package com.easyroutine.api.controller.v1.routine;

import com.easyroutine.api.controller.v1.routine.request.RoutineCreateRequest;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.RoutineService;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine.dto.RoutineListDto;
import com.easyroutine.global.response.ApiResponse;
import com.easyroutine.global.response.PageData;
import com.easyroutine.global.response.ResultType;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "루틴 API", description = "루틴 관련 API")
@RestController
@RequestMapping("/api/v1/routines")
@RequiredArgsConstructor
public class RoutineController {

	private final RoutineService routineService;

	@Operation(summary = "루틴 생성", description = "루틴 생성 API")
	@PostMapping()
	public ApiResponse<Long> createRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
			@Valid @RequestBody RoutineCreateRequest routineCreateRequest) {

		routineCreateRequest.ofRoutineCreateRequest();
		String memberId = customOAuth2User.getMemberId();

		routineCreateRequest.getRoutineDto().setMemberIdFromToken(memberId);

		return ApiResponse.success(routineService.createRoutine(routineCreateRequest.getRoutineDto()));
	}

	@Operation(summary = "루틴 조회", description = "루틴 조회 API")
	@GetMapping
	public PageData<RoutineListDto> findAllRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		Member member = Member.of(customOAuth2User.getMemberId());
		List<RoutineListDto> list = routineService.findAllRoutine(member);
		return PageData.of(list.size(), list);
	}

	@Operation(summary = "루틴 수정", description = "루틴 수정 API")
	@PutMapping("/{routineId}")
	public ApiResponse<Long> updateRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
			@PathVariable long routineId, @Valid @RequestBody RoutineCreateRequest routineCreateRequest) {

		routineCreateRequest.ofRoutineCreateRequest();
		String memberId = customOAuth2User.getMemberId();

		routineCreateRequest.getRoutineDto().setMemberIdFromToken(memberId);
		Long updatedRoutineId = routineService.updateRoutine(routineId, routineCreateRequest.getRoutineDto());

		if (updatedRoutineId == null) {
			return ApiResponse.fail(ResultType.FAIL);
		}

		return ApiResponse.success(updatedRoutineId);
	}

	@Operation(summary = "루틴 제거", description = "루틴 삭제 API")
	@DeleteMapping("/{routineId}")
	public ApiResponse<RoutineDto> deleteRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
			@PathVariable long routineId) {
		Member member = Member.of(customOAuth2User.getMemberId());
		RoutineDto dto = routineService.deleteRoutine(routineId, member);
		if (dto == null) {
			return ApiResponse.fail(ResultType.FAIL);
		}
		return ApiResponse.success(dto);
	}

}
