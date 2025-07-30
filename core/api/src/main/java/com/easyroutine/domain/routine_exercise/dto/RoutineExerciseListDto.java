package com.easyroutine.domain.routine_exercise.dto;

import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetListDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseListDto {

	private Long id;

	private int order;

	private RoutineExerciseDetailDto exercise;

	private List<RoutineExerciseSetListDto> sets;

}
