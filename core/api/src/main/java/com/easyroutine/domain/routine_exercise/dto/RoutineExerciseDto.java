package com.easyroutine.domain.routine_exercise.dto;

import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import lombok.Getter;

import java.util.List;

@Getter
public class RoutineExerciseDto {
    private Long id;
    private Long routineId;
    private Long ExerciseId;
    private int order;
    private List<RoutineExerciseSetsDto> setsDtoList;
}
