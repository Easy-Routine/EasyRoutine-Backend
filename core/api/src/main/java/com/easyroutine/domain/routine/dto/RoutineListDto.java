package com.easyroutine.domain.routine.dto;

import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseListDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineListDto {

	private Long id;

	private String name;

	private String color;

	private int order;

	private List<RoutineExerciseListDto> routineExercises;

}
