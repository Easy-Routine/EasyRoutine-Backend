package com.easyroutine.domain.routine.dto;

import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;

import java.util.List;

public class RoutineListDto {
    private Long id;
    private String name;
    private int order;
    private String color;
    private List<RoutineExerciseDto> routineExerciseDtoList;
}
