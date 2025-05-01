package com.easyroutine.domain.routine_exercise_sets.dto;

import lombok.Getter;

@Getter
public class RoutineExerciseSetsDto {
    private Long id;
    private Long routineExerciesId;
    private int order;
    private Double weight;
    private int rep;
    private String refreshTime; // mm:ss
}
