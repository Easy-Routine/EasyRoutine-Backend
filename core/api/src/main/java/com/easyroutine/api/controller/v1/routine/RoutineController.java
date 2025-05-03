package com.easyroutine.api.controller.v1.routine;

import com.easyroutine.api.controller.v1.routine.request.RoutineCreateRequest;
import com.easyroutine.api.security.oauth.CustomOAuth2User;
import com.easyroutine.domain.routine.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "루틴 API", description = "루틴 관련 API")
@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;


    @Operation(summary = "루틴 생성", description = "루틴 생성 API")
    @PostMapping
    public Long createRoutine(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, @RequestBody RoutineCreateRequest routineCreateRequest){
        String memberId = customOAuth2User.getMemberId();
        routineCreateRequest.getRoutineDto().setMemberIdFromToken(memberId);
        return routineService.createRoutine(routineCreateRequest.getRoutineDto());
    }
}
