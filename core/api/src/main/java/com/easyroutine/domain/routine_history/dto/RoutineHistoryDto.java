package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineExerciseHistoryCreateRequest;
import com.easyroutine.api.controller.v1.routine_history.request.RoutineExerciseHistoryUpdateRequest;
import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryCreateRequest;
import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryUpdateRequest;
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

    private List<@Valid RoutineHistoryDetailsDto> sets;

    public static RoutineHistoryDto of(RoutineHistory routineHistory) {
        List<RoutineHistoryDetailsDto> routineHistoryDetailsDtos = routineHistory.getRoutineHistoryDetails().stream()
                .map(RoutineHistoryDetailsDto::of)
                .toList();

        return RoutineHistoryDto.builder()
                .id(routineHistory.getId())
                .name(routineHistory.getRoutine().getName())
                .color(routineHistory.getRoutine().getColor())
                .sets(routineHistoryDetailsDtos)
                .build();
    }

    public static RoutineHistoryDto of(RoutineHistoryCreateRequest request,
                            RoutineExerciseHistoryCreateRequest historyRequest) {
        List<RoutineHistoryDetailsDto> routineHistoryDetailsDtos = historyRequest.getSets().stream()
                .map(historyDetail -> RoutineHistoryDetailsDto.of(historyDetail))
                .toList();

        return RoutineHistoryDto.builder()
                .routineId(request.getRoutineId())
                .name(request.getName())
                .color(request.getColor())
                .exerciseId(historyRequest.getExercise().getExerciseId())
                .sets(routineHistoryDetailsDtos)
                .build();
    }

    public static RoutineHistoryDto of(Long id, RoutineHistoryUpdateRequest request, RoutineExerciseHistoryUpdateRequest historyRequest) {
        List<RoutineHistoryDetailsDto> routineHistoryDetailsDtos = historyRequest.getSets().stream()
                .map(historyDetail -> RoutineHistoryDetailsDto.of(historyDetail))
                .toList();

        return RoutineHistoryDto.builder()
                .id(id)
                .routineId(request.getRoutineId())
                .name(request.getName())
                .color(request.getColor())
                .exerciseId(historyRequest.getExercise().getExerciseId())
                .sets(routineHistoryDetailsDtos)
                .build();
    }
}
