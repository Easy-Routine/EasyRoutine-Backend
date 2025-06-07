package com.easyroutine.api.controller.v1.routine;

import com.easyroutine.api.controller.v1.routine.request.RoutineCreateRequest;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import com.easyroutine.domain.routine.RoutineService;
import com.easyroutine.global.response.ApiResponse;
import com.easyroutine.global.response.ResultType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "루틴 API", description = "루틴 관련 API")
@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;


    @Operation(summary = "루틴 생성", description = "루틴 생성 API")
    @PostMapping()
    public ApiResponse<Long> createRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, @Valid @RequestBody RoutineCreateRequest routineCreateRequest) {
        String memberId = customOAuth2User.getMemberId();


        routineCreateRequest.getRoutineDto().setMemberIdFromToken(memberId);
        return ApiResponse.success(routineService.createRoutine(routineCreateRequest.getRoutineDto()));
    }


    @Operation(summary = "루틴 조회", description = "루틴 조회 API")
    @GetMapping
    public ApiResponse<List<RoutineDto>> findAllRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        Member member = Member.of(customOAuth2User.getMemberId());
        List<RoutineDto> list = routineService.findAllRoutine(member);

        if (list.isEmpty()) {
            return ApiResponse.fail(ResultType.DATA_NOT_FOUND);
        }

        return ApiResponse.success(list);
    }

    @Operation(summary = "루틴 제거", description = "루틴 삭제 API")
    @DeleteMapping("/{routineId}")
    public ApiResponse<RoutineDto> deleteRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, @PathVariable long routineId) {
        Member member = Member.of(customOAuth2User.getMemberId());
        RoutineDto dto = routineService.deleteRoutine(routineId, member);
        if (dto == null){
            return ApiResponse.fail(ResultType.FAIL);
        }
        return ApiResponse.success(dto);
    }
}
