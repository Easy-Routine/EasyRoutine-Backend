package com.easyroutine.domain.exercises;

import com.easyroutine.global.exception.BusinessException;
import com.easyroutine.global.response.ResultType;
import lombok.Getter;

import java.util.List;

@Getter
public enum ExerciseType {
    WEIGHT, COUNT, TIME;

    public static List<ExerciseType> convertToEnum(List<String> types) {
        try{
            return types.stream()
                    .map(ExerciseType::valueOf)
                    .toList();
        }catch (IllegalArgumentException e){
            throw new BusinessException(ResultType.INPUT_ERROR, "운동 타입 입력이 잘못되었습니다." + types);
        }
    }
}
