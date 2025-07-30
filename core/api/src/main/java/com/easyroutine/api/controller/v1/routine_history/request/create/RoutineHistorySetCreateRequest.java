package com.easyroutine.api.controller.v1.routine_history.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistorySetCreateRequest {

	@Schema(description = "루틴 히스토리 세트 순서", example = "1")
	private int order;

	@Schema(description = "무게(kg)", example = "1")
	private double weight;

	@Schema(description = "횟수(개수)", example = "1")
	private int rep;

	@Schema(description = "운동시간(초)", example = "1")
	private int exerciseSec;

	@Schema(description = "휴식시간(초)", example = "1")
	private int restSec;

}
