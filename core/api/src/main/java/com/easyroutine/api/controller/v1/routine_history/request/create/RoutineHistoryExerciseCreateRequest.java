package com.easyroutine.api.controller.v1.routine_history.request.create;

import com.easyroutine.api.controller.v1.routine_history.request.RoutineHistoryExerciseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryExerciseCreateRequest {

	@Schema(description = "운동 이력 순서", example = "1")
	private int order;

	@Schema(description = "운동 이력 운동 정보")
	private RoutineHistoryExerciseRequest exercise;

	@Schema(description = "운동 이력 세트 목록")
	private List<RoutineHistorySetCreateRequest> sets;

}
