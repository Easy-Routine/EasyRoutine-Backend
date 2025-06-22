package com.easyroutine.api.controller.v1.routine_history.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoutineHistoryUpdateRequest {

	@Schema(description = "루틴 아이디", example = "1")
	private long routineId;

	@Schema(description = "루틴 명", example = "월요일 상체")
	private String name;

	@Schema(description = "루틴 색상", example = "#FF5733")
	private String color;

	@Schema(description = "루틴 운동 이력 기록리스트",
			example = "[" + "{  " + "   \"exerciseId\": 1," + "   \"order\": 1," + "   \"routineHistoryDetails\": ["
					+ "       {" + "           \"id\": 1, " + "           \"order\": 1, "
					+ "           \"weight\": 20.0, " + "           \"rep\": 10, " + "           \"restSec\": 30"
					+ "       }" + "    ]" + "}" + "]")
	private List<RoutineExerciseHistoryUpdateRequest> routineExercises;

}
