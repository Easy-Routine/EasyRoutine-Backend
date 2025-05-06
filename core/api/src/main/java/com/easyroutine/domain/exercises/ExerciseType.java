package com.easyroutine.domain.exercises;

import lombok.Getter;

import java.util.List;

@Getter
public enum ExerciseType {
    WEIGHT, COUNT, TIME;

    public static List<ExerciseType> convertToEnum(List<String> types) {
        return types.stream()
                .map(ExerciseType::valueOf)
                .toList();
    }
}
