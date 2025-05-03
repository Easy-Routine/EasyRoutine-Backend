package com.easyroutine.domain.routine_exercise;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.routine.Routine;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class RoutineExerciseMapper {

    public RoutineExercise toEntity(RoutineExerciseDto routineExerciseDto){
        return RoutineExercise.builder()
                .routine(Routine.of(routineExerciseDto.getRoutineId()))
                .exercise(Exercise.of(routineExerciseDto.getExerciseId()))
                .order(routineExerciseDto.getOrder())
                .build();
    }
}
