package com.easyroutine.domain.routine_history.dto;


import com.easyroutine.api.controller.v1.routine_history.request.create.RoutineHistoryExerciseCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistoryExercise;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryExerciseDto {

    private int order;

    private Long exerciseId;

    List<RoutineHistorySetsDto> sets;

    public static RoutineHistoryExerciseDto createOf(RoutineHistoryExerciseCreateRequest request) {
        List<RoutineHistorySetsDto> sets = request.getSets().stream()
                .map(RoutineHistorySetsDto::createOf)
                .toList();

        return RoutineHistoryExerciseDto.builder()
                .order(request.getOrder())
                .exerciseId(request.getExercise().getId())
                .sets(sets)
                .build();
    }

    public static RoutineHistoryExerciseDto of(RoutineHistoryExercise routineHistoryExercise) {
        List<RoutineHistorySetsDto> sets = routineHistoryExercise.getRoutineHistoryExerciseSets().stream()
                .map(RoutineHistorySetsDto::of)
                .toList();

        return RoutineHistoryExerciseDto.builder()
                .order(routineHistoryExercise.getOrderIndex())
                .exerciseId(routineHistoryExercise.getExercise().getId())
                .sets(sets)
                .build();
    }
}
