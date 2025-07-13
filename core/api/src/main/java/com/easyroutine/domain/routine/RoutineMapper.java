package com.easyroutine.domain.routine;

import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine.dto.RoutineListDto;
import com.easyroutine.domain.routine_exercise.RoutineExerciseMapper;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutineMapper {

	private final RoutineExerciseMapper routineExerciseMapper;

	public Routine toEntity(RoutineDto routineDto) {
		return Routine.builder()
			.member(Member.of(routineDto.getMemberId()))
			.name(routineDto.getName())
			.color(routineDto.getColor())
			.order(routineDto.getOrder())
			.build();

	}

	public RoutineDto fromEntity(Routine e) {
		List<RoutineExerciseDto> routineExerciseDtoList = e.getRoutineExercises()
			.stream()
			.map(routineExerciseMapper::fromEntity)
			.toList();
		return RoutineDto.builder()
			.id(e.getId())
			.order(e.getOrder())
			.memberId(e.getMember().getId())
			.name(e.getName())
			.color(e.getColor())
			.routineExerciseDtoList(routineExerciseDtoList)
			.build();
	}

	public RoutineListDto fromEntityToListDto(Routine e) {
		List<RoutineExerciseListDto> routineExerciseListDto = e.getRoutineExercises()
			.stream()
			.map(routineExerciseMapper::fromEntityToListDto)
			.toList();
		return RoutineListDto.builder()
			.id(e.getId())
			.name(e.getName())
			.color(e.getColor())
			.order(e.getOrder())
			.routineExercises(routineExerciseListDto)
			.build();
	}

}
