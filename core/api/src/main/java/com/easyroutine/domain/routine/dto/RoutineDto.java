package com.easyroutine.domain.routine.dto;

import com.easyroutine.domain.exercises.dto.ExercisesDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class RoutineDto {
    private Long id;
    private String memberId;
    private String name;
    private String color;
    private List<RoutineExerciseDto> routineExerciseDtoList;
}
