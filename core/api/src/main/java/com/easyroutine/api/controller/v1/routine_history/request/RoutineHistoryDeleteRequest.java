package com.easyroutine.api.controller.v1.routine_history.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryDeleteRequest {

	@Schema(description = "루틴 히스토리 ID", example = "1")
	private Long id; // 루틴 히스토리 ID

}
