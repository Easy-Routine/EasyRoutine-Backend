package com.easyroutine.domain.sets.dto;

import lombok.Getter;

@Getter
public class SetsDto {
    private Long id;
    private Long routineExerciesId;
    private Double weight;
    private int rep;
    private String refreshTime; // mm:ss
}
