package com.easyroutine.api.controller.v1.routine_history.request.create;

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
public class RoutineHistoryCreateRequest {

	@Schema(description = "루틴 ID", example = "1")
	private Long routineId;

	@Schema(description = "루틴 히스토리 이름", example = "오늘의 루틴")
	private String name;

	@Schema(description = "루틴 히스토리 색상", example = "#FF5733")
	private String color;

	@Schema(description = "루틴 히스토리 순서", example = "1")
	private int order;

	@Schema(description = "루틴 히스토리 운동 시간 (초)", example = "3600")
	private int workoutTime; // in seconds

	@Schema(description = "루틴 히스토리 운동 목록")
	List<RoutineHistoryExerciseCreateRequest> routineExercises;

}
