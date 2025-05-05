package com.easyroutine.domain.exercises;

import com.easyroutine.global.exception.BusinessException;
import lombok.Getter;

import static com.easyroutine.global.response.ResultType.INPUT_ERROR;

@Getter
public enum ExerciseCategory {
    CHEST, BACK, SHOULDER, LEG, ARM, ETC;

    public static ExerciseCategory convertToEnum(String type) {
        try {
            return ExerciseCategory.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(INPUT_ERROR, "Invalid exercise part: " + type);
        }
    }

    public String getType() {
        return this.name().toUpperCase();
    }
}
