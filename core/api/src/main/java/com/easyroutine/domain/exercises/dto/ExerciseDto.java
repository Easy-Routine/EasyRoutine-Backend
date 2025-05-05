package com.easyroutine.domain.exercises.dto;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.ExerciseType;
import com.easyroutine.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExerciseDto {

    private Long id;
    private Member member;
    private String name;
    private String image;
    private String originImage;
    private ExerciseCategory category;
    private List<ExerciseType> types;
    private int isEditable;
    private int shareLevel;

    @Builder
    public ExerciseDto(Long id, Member member, String name, String image, String originImage, ExerciseCategory category, List<ExerciseType> types, int isEditable, int shareLevel) {
        this.id = id;
        this.member = member;
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
                .member(exercise.getMember())
                .name(exercise.getName())
                .image(exercise.getImage())
                .originImage(exercise.getOriginImage())
                .category(exercise.getCategory())
                .types(exercise.getTypes())
                .isEditable(exercise.getIsEditable())
                .shareLevel(exercise.getShareLevel())
                .build();
    }
}
