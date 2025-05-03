package com.easyroutine.domain.exercises.dto;

import lombok.Getter;

@Getter
public class ExercisesDto {
    private Long id;
    private String registerId;
    private String name;
    private String image;
    private String originImage;
    private String category;
    private int isEditable;
    private int shareLevel;
}
