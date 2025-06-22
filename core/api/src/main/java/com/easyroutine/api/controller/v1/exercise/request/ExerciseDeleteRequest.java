package com.easyroutine.api.controller.v1.exercise.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDeleteRequest {

	@NotBlank(message = "운동 ID는 비어 있을 수 없습니다.")
	private Long id;

}
