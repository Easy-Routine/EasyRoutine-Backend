package com.easyroutine.api.controller.v1.exercise.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCreateRequest {

	@Schema(description = "운동 이름", example = "벤치프레스")
	@NotBlank(message = "운동 이름은 비어 있을 수 없습니다.")
	private String name;

	@Schema(description = "운동 종류", example = "[\"WEIGHT\"]")
	@NotEmpty(message = "운동 이미지는 비어 있을 수 없습니다.")
	private List<String> types;

	@Schema(description = "운동 카테고리", example = "CHEST")
	@NotBlank(message = "운동 카테고리는 비어 있을 수 없습니다.")
	private String category;

	private String imageUrl;

}
