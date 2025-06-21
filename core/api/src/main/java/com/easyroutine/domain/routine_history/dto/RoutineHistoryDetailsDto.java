package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryDetailsCreateRequest;
import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryDetailsUpdateRequest;
import com.easyroutine.domain.routine_history.RoutineHistoryDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineHistoryDetailsDto {

	private Long id;

	private int order;

	private double weight;

	private int rep;

	private int exerciseSec;

	private int refreshTime;

	public static RoutineHistoryDetailsDto of(RoutineHistoryDetails routineHistoryDetails) {
		return RoutineHistoryDetailsDto.builder()
			.id(routineHistoryDetails.getId())
			.order(routineHistoryDetails.getOrder())
			.weight(routineHistoryDetails.getWeight())
			.rep(routineHistoryDetails.getRep())
			.exerciseSec(routineHistoryDetails.getExerciseTime())
			.refreshTime(routineHistoryDetails.getRefreshTime())
			.build();
	}

	public static RoutineHistoryDetailsDto of(RoutineHistoryDetailsCreateRequest request) {
		return RoutineHistoryDetailsDto.builder()
			.order(request.getOrder())
			.weight(request.getWeight())
			.rep(request.getRep())
			.exerciseSec(request.getExerciseSec())
			.refreshTime(request.getRestSec())
			.build();
	}

	public static RoutineHistoryDetailsDto of(RoutineHistoryDetailsUpdateRequest historyDetail) {
		return RoutineHistoryDetailsDto.builder()
			.id(historyDetail.getId())
			.order(historyDetail.getOrder())
			.weight(historyDetail.getWeight())
			.rep(historyDetail.getRep())
			.exerciseSec(historyDetail.getExerciseSec())
			.refreshTime(historyDetail.getRestSec())
			.build();
	}

}
