package com.easyroutine.api.controller.v1.routine_history;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryDeleteRequest;
import com.easyroutine.api.controller.v1.routine_history.request.create.RoutineHistoryCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.dto.HistorySummaryDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.service.RoutineHistoryService;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<RoutineHistoryDto> getRoutineHistories(
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") @RequestParam(name = "date") String date,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        List<RoutineHistory> histories = routineHistoryService.getRoutineHistories(memberId, date);
        return histories.stream()
                .map(RoutineHistoryDto::of)
                .toList();
    }

    @Operation(summary = "루틴 히스토리 요약 조회", description = "루틴 히스토리 요약 조회 API")
    @GetMapping("/summary")
    public HistorySummaryDto getRoutineHistorySummary(
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") @RequestParam(name = "date") String date,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        String memberId = user.getMemberId();
        return routineHistoryService.getRoutineHistorySummary(memberId, date);
    }
}
