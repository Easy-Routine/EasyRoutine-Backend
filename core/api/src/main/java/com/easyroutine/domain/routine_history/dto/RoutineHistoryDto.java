package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineExerciseHistoryRequest;
import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistory;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineHistoryDto {

    private Long id;

    private Long routineId;

    private Long exerciseId;

    private String memberId;

    private String name;

    private String color;

    private LocalDateTime exerciseDate;

    private List<@Valid RoutineHistoryDetailsDto> routineHistoryDetails;

    public static RoutineHistoryDto of(RoutineHistory routineHistory) {
        List<RoutineHistoryDetailsDto> routineHistoryDetailsDtos = routineHistory.getRoutineHistoryDetails().stream()
                .map(RoutineHistoryDetailsDto::of)
                .toList();

        return RoutineHistoryDto.builder()
                .id(routineHistory.getId())
                .name(routineHistory.getRoutine().getName())
                .color(routineHistory.getRoutine().getColor())
                .routineHistoryDetails(routineHistoryDetailsDtos)
                .build();
    }

    public static RoutineHistoryDto of(RoutineHistoryCreateRequest request,
                            RoutineExerciseHistoryRequest historyRequest) {
        List<RoutineHistoryDetailsDto> routineHistoryDetailsDtos = historyRequest.getRoutineHistoryDetails().stream()
                .map(historyDetail -> RoutineHistoryDetailsDto.of(historyDetail))
                .toList();

        return RoutineHistoryDto.builder()
                .routineId(request.getRoutineId())
                .name(request.getName())
                .color(request.getColor())
                .exerciseId(historyRequest.getExerciseId())
                .routineHistoryDetails(routineHistoryDetailsDtos)
                .build();
    }
}
