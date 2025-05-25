package com.easyroutine.api.controller.v1.routine_history;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryService;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistorySummaryDto;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "루틴 이력 API", description = "루틴 이력 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/routine/histories")
@RequiredArgsConstructor
public class RoutineHistoryController {

    private final RoutineHistoryService routineHistoryService;

    @Operation(summary = "루틴 이력 요약 조회", description = "루틴 이력 요약을 조회합니다.")
    @GetMapping("/summary/{date}")
    public RoutineHistorySummaryDto getRoutineHistories(@PathVariable("date") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.") String date) {
        int exerciseTime = routineHistoryService.getExerciseTime(date);
        int totalVolume = routineHistoryService.getTotalVolume(date);
        return RoutineHistorySummaryDto.of(exerciseTime, totalVolume);
    }

    @Operation(summary = "루틴 이력 조회", description = "루틴 이력을 조회합니다.")
    @GetMapping("/{date}")
    public List<RoutineHistoryDto> getRoutineHistory(@PathVariable("date") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.") String date) {
        List<RoutineHistory> routineHistories = routineHistoryService.getRoutineHistory(date);
        return routineHistories.stream()
                .map(routineHistory -> RoutineHistoryDto.of(routineHistory)
                ).toList();
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
