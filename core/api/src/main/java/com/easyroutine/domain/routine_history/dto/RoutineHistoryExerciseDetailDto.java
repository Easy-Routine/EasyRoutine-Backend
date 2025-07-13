package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.ExerciseType;
import com.easyroutine.domain.routine_history.RoutineHistoryExercise;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryExerciseDetailDto {
    private Long id;
    private String image;
    private String name;
    private ExerciseCategory category;
    private List<ExerciseType> types;
    private int isEditable;
    private int shareLevel;


    public static RoutineHistoryExerciseDetailDto createOf(Long id, String imageUrl) {
        return RoutineHistoryExerciseDetailDto.builder()
                .id(id)
                .image(imageUrl)
                .build();
    }

    public static RoutineHistoryExerciseDetailDto createOf(RoutineHistoryExercise routineHistoryExercise) {
        Exercise exercise = routineHistoryExercise.getExercise();
        String exerciseName = routineHistoryExercise.getExerciseName();
        List<ExerciseType> exerciseType = routineHistoryExercise.getExerciseType();
        ExerciseCategory exerciseCategory = routineHistoryExercise.getExerciseCategories();
        return RoutineHistoryExerciseDetailDto.builder()
                .id(exercise.getId())
                .image(exercise.getImage())
                .name(exerciseName)
                .category(exerciseCategory)
                .types(exerciseType)
                .isEditable(exercise.getIsEditable())
                .shareLevel(exercise.getShareLevel())
                .build();
    }
}
