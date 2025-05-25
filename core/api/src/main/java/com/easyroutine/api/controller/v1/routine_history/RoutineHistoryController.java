package com.easyroutine.api.controller.v1.routine_history;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryService;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistorySummaryDto;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/routine/histories")
@RequiredArgsConstructor
public class RoutineHistoryController {

    private final RoutineHistoryService routineHistoryService;

    @GetMapping("/summary/{date}")
    public RoutineHistorySummaryDto getRoutineHistories(@PathVariable("date") String date) {
        //todo : 날짜 형식 체크
        int exerciseTime = routineHistoryService.getExerciseTime(date);
        int totalVolume = routineHistoryService.getTotalVolume(date);
        return RoutineHistorySummaryDto.of(exerciseTime, totalVolume);
    }

    @GetMapping("/{date}")
    public List<RoutineHistoryDto> getRoutineHistory(@PathVariable("date") String date) {
        //todo : 날짜 형식 체크
        List<RoutineHistory> routineHistories = routineHistoryService.getRoutineHistory(date);
        routineHistories.stream()
                .map(routineHistory -> RoutineHistoryDto.of(routineHistory)
                );

        return null;
    }

    @Operation(summary = "루틴 이력 기록", description = "루틴 이력을 기록합니다.")
    @PostMapping
    public String createRoutineHistory(@RequestBody RoutineHistoryCreateRequest request,
                                    @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        List<RoutineHistoryDto> routineHistoryDtos = request.getRoutineExercises().stream()
                .map(historyRequest -> RoutineHistoryDto.of(request, historyRequest))
                .toList();
        return routineHistoryService.createRoutineHistory(routineHistoryDtos, memberId);
    }
}
