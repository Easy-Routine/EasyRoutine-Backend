package com.easyroutine.domain.routine.dto;

import com.easyroutine.domain.exercises.dto.ExercisesDto;
import lombok.Getter;

import java.util.List;

@Getter
public class RoutineDto {
    private Long id;
    private Long memberId;
    private String name;
    private String color;
    private List<ExercisesDto> exercisesDtoList;
}
