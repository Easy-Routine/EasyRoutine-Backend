package com.easyroutine.domain.exercises.dto;

import com.easyroutine.domain.sets.dto.SetsDto;
import lombok.Getter;

import java.util.List;

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

    private List<SetsDto> setsDtoList;
}
