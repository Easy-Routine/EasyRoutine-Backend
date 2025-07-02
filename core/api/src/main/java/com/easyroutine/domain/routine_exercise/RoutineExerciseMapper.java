package com.easyroutine.domain.routine_exercise;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.routine.Routine;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSetsMapper;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutineExerciseMapper {

	private final RoutineExerciseSetsMapper routineExerciseSetsMapper;

	public RoutineExercise toEntity(RoutineExerciseDto routineExerciseDto) {
		return RoutineExercise.builder()
			.routine(Routine.of(routineExerciseDto.getRoutineId()))
			.exercise(Exercise.of(routineExerciseDto.getExerciseId()))
			.order(routineExerciseDto.getOrder())
			.build();
	}

	public RoutineExerciseDto fromEntity(RoutineExercise e) {
		List<RoutineExerciseSetsDto> setsDtoList = e.getSets()
			.stream()
			.map(routineExerciseSetsMapper::fromEntity)
			.toList();
		return RoutineExerciseDto.builder()
			.id(e.getId())
			.routineId(e.getRoutine().getId())
			.exerciseId(e.getExercise().getId())
			.category(e.getExercise().getCategory())
			.image(e.getExercise().getImage())
			.name(e.getExercise().getName())
			.order(e.getOrder())
			.routineExerciseSetsDtoList(setsDtoList)
			.build();
	}

}
