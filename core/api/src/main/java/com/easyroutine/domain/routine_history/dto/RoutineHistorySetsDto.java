package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.api.controller.v1.routine_history.request.create.RoutineHistorySetCreateRequest;
import com.easyroutine.domain.routine_history.RoutineHistoryExerciseSets;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistorySetsDto {

	private int order;

	private double weight; // 운동 세트 중량 (kg 단위)

	private int reps; // 운동 세트 반복 횟수

	private int exerciseTime; // 운동 세트 시간 (초 단위)

	private int restSec; // 운동 세트 휴식 시간 (초 단위)

	public static RoutineHistorySetsDto createOf(RoutineHistorySetCreateRequest request) {
		return RoutineHistorySetsDto.builder()
			.order(request.getOrder())
			.weight(request.getWeight())
			.reps(request.getRep())
			.exerciseTime(request.getExerciseSec())
			.restSec(request.getRestSec())
			.build();
	}

	public static RoutineHistorySetsDto of(RoutineHistoryExerciseSets routineHistoryExerciseSets) {
		return RoutineHistorySetsDto.builder()
			.order(routineHistoryExerciseSets.getOrderIndex())
			.weight(routineHistoryExerciseSets.getWeight())
			.reps(routineHistoryExerciseSets.getReps())
			.exerciseTime(routineHistoryExerciseSets.getExerciseTime())
			.restSec(routineHistoryExerciseSets.getRefreshTime())
			.build();
	}

}
