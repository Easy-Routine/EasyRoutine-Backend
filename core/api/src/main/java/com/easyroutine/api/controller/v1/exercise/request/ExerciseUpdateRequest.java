package com.easyroutine.api.controller.v1.exercise.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseUpdateRequest {

    @Positive(message = "운동 ID는 양수여야 합니다.")
    @NotNull(message = "운동 ID는 비어 있을 수 없습니다.")
    private Long id;

    @NotBlank(message = "운동 이름은 비어 있을 수 없습니다.")
    private String name;

    @NotEmpty(message = "운동 이미지는 비어 있을 수 없습니다.")
    private List<String> types;

    @NotBlank(message = "운동 카테고리는 비어 있을 수 없습니다.")
    private String category;

    private String imageUrl;
}
