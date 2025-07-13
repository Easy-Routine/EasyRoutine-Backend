package com.easyroutine.domain.routine_exercise.dto;

import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.ExerciseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseDetailDto {
    private Long id;
    private String image;
    private String name;
    private ExerciseCategory category;
    private List<ExerciseType> types;
    private int isEditable;
    private int shareLevel;
}
