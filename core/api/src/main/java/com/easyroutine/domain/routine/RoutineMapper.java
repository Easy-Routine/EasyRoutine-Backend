package com.easyroutine.domain.routine;

import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.RoutineExerciseMapper;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
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

}
