package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.api.controller.v1.routine_history.request.create.RoutineHistoryCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryDto {

    private Long historyId;

    private Long routineId;

    private String name;

    private String color;

    private int order;

    private int workoutTime;

    private List<RoutineHistoryExerciseDto> routineExercises;

    public static RoutineHistoryDto createOf(RoutineHistoryCreateRequest request) {
        List<RoutineHistoryExerciseDto> routineExercises = request.getRoutineExercises().stream()
                .map(RoutineHistoryExerciseDto::createOf)
                .toList();

        return RoutineHistoryDto.builder()
                .routineId(request.getRoutineId())
                .name(request.getName())
                .color(request.getColor())
                .order(request.getOrder())
                .workoutTime(request.getWorkoutTime())
                .routineExercises(routineExercises)
                .build();
    }

    public static RoutineHistoryDto of(RoutineHistory routineHistory) {

        int totalExerciseTime = routineHistory.getRoutineHistoryExercises().stream()
                .flatMap(exercise -> exercise.getRoutineHistoryExerciseSets().stream())
                .mapToInt(set -> set.getExerciseTime())
                .sum();

        List<RoutineHistoryExerciseDto> routineExercises = routineHistory.getRoutineHistoryExercises().stream()
                .map(RoutineHistoryExerciseDto::of)
                .toList();

        return RoutineHistoryDto.builder()
                .historyId(routineHistory.getId())
                .routineId(routineHistory.getRoutine().getId())
                .name(routineHistory.getRoutine().getName())
                .color(routineHistory.getRoutine().getColor())
                .order(routineHistory.getRoutine().getOrder())
                .workoutTime(totalExerciseTime)
                .routineExercises(routineExercises)
                .build();
    }
}
