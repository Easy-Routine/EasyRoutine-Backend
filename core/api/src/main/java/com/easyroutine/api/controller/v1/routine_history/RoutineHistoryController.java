package com.easyroutine.api.controller.v1.routine_history;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryDeleteRequest;
import com.easyroutine.api.controller.v1.routine_history.request.create.RoutineHistoryCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryPeriod;
import com.easyroutine.domain.routine_history.RoutineHistoryType;
import com.easyroutine.domain.routine_history.dto.HistoryStatisticDto;
import com.easyroutine.domain.routine_history.dto.HistorySummaryDto;
import com.easyroutine.domain.routine_history.dto.MonthlyRoutineHistoryDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.service.RoutineHistoryService;
import com.easyroutine.global.response.PageData;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "루틴 히스토리 API", description = "루틴 히스토리 관련 API")
@RestController
@RequestMapping("/api/v1/routines/histories")
@RequiredArgsConstructor
public class RoutineHistoryController {

    private final RoutineHistoryService routineHistoryService;

    @Operation(summary = "루틴 히스토리 생성", description = "루틴 히스토리 생성 API")
    @PostMapping
    public Long createRoutineHistory(
            @RequestBody RoutineHistoryCreateRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        RoutineHistoryDto routineHistoryDto = RoutineHistoryDto.createOf(request);
        return routineHistoryService.createRoutineHistory(routineHistoryDto, memberId);
    }

    @Operation(summary = "루틴 히스토리 삭제", description = "루틴 히스토리 삭제 API")
    @DeleteMapping
    public Long deleteRoutineHistory(
            @RequestBody RoutineHistoryDeleteRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        return routineHistoryService.deleteRoutineHistory(request.getId(), memberId);
    }

    @Operation(summary = "루틴 히스토리 목록 조회", description = "루틴 히스토리 목록 조회 API")
    @GetMapping
    public PageData<RoutineHistoryDto> getRoutineHistories(
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") @RequestParam(name = "date") String date,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        List<RoutineHistory> histories = routineHistoryService.getRoutineHistories(memberId, date);
        List<RoutineHistoryDto> historyDtos = histories.stream()
                .map(RoutineHistoryDto::of)
                .toList();
        return PageData.of(historyDtos.size(), historyDtos);
    }

    @Operation(summary = "루틴 히스토리 상세 조회", description = "루틴 히스토리 상세 조회 API")
    @GetMapping("/{historyId}")
    public RoutineHistoryDto getRoutineHistory(
            @PathVariable Long historyId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        RoutineHistory history = routineHistoryService.getRoutineHistory(historyId);
        return RoutineHistoryDto.of(history);
    }

    @Operation(summary = "루틴 히스토리 요약 조회", description = "루틴 히스토리 요약 조회 API")
    @GetMapping("/summary")
    public HistorySummaryDto getRoutineHistorySummary(
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") @RequestParam(name = "date") String date,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        List<RoutineHistory> histories = routineHistoryService.getRoutineHistories(memberId, date);
        return HistorySummaryDto.of(histories);
    }

    @Operation(summary = "루틴 기간별 통계", description = "루틴 기간별 통계 API")
    @GetMapping("/statistics")
    public List<HistoryStatisticDto> getRoutineStatistics(
            @RequestParam Long exerciseId,
            @RequestParam RoutineHistoryPeriod period,
            @RequestParam RoutineHistoryType type,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        return routineHistoryService.getRoutineStatistics(exerciseId, memberId, period, type);
    }

    @Operation(summary = "월별 루틴 기록 조회", description = "월별 루틴 기록 조회 API")
    @GetMapping("/monthly")
    public Map<LocalDate, List<MonthlyRoutineHistoryDto>> getMonthlyRoutineHistories(
            @Pattern(regexp = "^\\d{4}-\\d{2}$") @RequestParam(name = "month") String month,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        List<RoutineHistory> histories = routineHistoryService.getMonthlyRoutineHistories(memberId, month);
        List<MonthlyRoutineHistoryDto> monthlyRoutineHistoryDtoList = histories.stream()
                .map(MonthlyRoutineHistoryDto::createByRoutineHistory)
                .toList();
        return monthlyRoutineHistoryDtoList.stream()
                .collect(Collectors.groupingBy(MonthlyRoutineHistoryDto::getExerciseDate));
    }
}
