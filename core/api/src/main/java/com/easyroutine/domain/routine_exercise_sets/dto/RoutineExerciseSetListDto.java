package com.easyroutine.domain.routine_exercise_sets.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseSetListDto {
    private Long id;
    private int order;
    private Double weight;
    private int rep;
    private int exerciseSec;
    private int restSec;
}
