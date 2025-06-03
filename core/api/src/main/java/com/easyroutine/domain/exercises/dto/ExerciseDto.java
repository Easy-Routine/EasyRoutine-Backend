package com.easyroutine.domain.exercises.dto;

import com.easyroutine.api.controller.v1.exercise.request.ExerciseCreateRequest;
import com.easyroutine.api.controller.v1.exercise.request.ExerciseUpdateRequest;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.ExerciseType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExerciseDto {

    private Long id;
    private String name;
    private String image;
    private String originImage;
    private ExerciseCategory category;
    private List<ExerciseType> types;
    private int isEditable;
    private int shareLevel;

    @Builder
    public ExerciseDto(Long id, String name, String image, String originImage, ExerciseCategory category, List<ExerciseType> types, int isEditable, int shareLevel) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.originImage = originImage;
        this.category = category;
        this.types = types;
        this.isEditable = isEditable;
        this.shareLevel = shareLevel;
    }

    public static ExerciseDto of(Exercise exercise) {
        return ExerciseDto.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .image(exercise.getImage())
                .originImage(exercise.getOriginImage())
                .category(exercise.getCategory())
                .types(exercise.getTypes())
                .isEditable(exercise.getIsEditable())
                .shareLevel(exercise.getShareLevel())
                .build();
    }

    public static ExerciseDto of(ExerciseCreateRequest request) {
        return ExerciseDto.builder()
                .name(request.getName())
                .image(request.getImageUrl())
                .category(ExerciseCategory.convertToEnum(request.getCategory()))
                .types(ExerciseType.convertToEnum(request.getTypes()))
                .isEditable(1)
                .shareLevel(1)
                .build();
    }

    public static ExerciseDto of(ExerciseUpdateRequest request) {
        return ExerciseDto.builder()
                .id(request.getId())
                .name(request.getName())
                .image(request.getImageUrl())
                .category(ExerciseCategory.convertToEnum(request.getCategory()))
                .types(ExerciseType.convertToEnum(request.getTypes()))
                .isEditable(1)
                .shareLevel(1)
                .build();
    }
}
