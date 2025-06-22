package com.easyroutine.domain.routine_exercise_sets.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseSetsDto {

	@JsonIgnore
	private Long id;

	@JsonIgnore
	private Long routineExerciesId;

	@Min(value = 1, message = "세트 순서는 1 이상이어야 합니다.")
	private int order;

	@NotNull(message = "무게는 필수 입니다.")
	private Double weight;

	@Min(value = 1, message = "반복 수는 1 이상이어야 합니다.")
	private int rep;

	@NotBlank(message = "휴식 시간은 비어 있을 수 없습니다.")
	private String restSec; // mm:ss

	public void setRoutineExerciesId(Long routineExerciesId) {
		this.routineExerciesId = routineExerciesId;
	}

}
