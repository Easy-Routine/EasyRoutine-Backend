package com.easyroutine.domain.routine_exercise_sets;

import com.easyroutine.domain.routine_exercise.RoutineExercise;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import org.springframework.stereotype.Component;

@Component
public class RoutineExerciseSetsMapper {

    public RoutineExerciseSets toEntity(RoutineExerciseSetsDto routineExerciseSetsDto){
        return RoutineExerciseSets.builder()
                .routineExercise(RoutineExercise.of(routineExerciseSetsDto.getRoutineExerciesId()))
                .order(routineExerciseSetsDto.getOrder())
                .weight(routineExerciseSetsDto.getWeight())
                .rep(routineExerciseSetsDto.getRep())
                .restSec(routineExerciseSetsDto.getRestSec())
                .build();
    }

    public RoutineExerciseSetsDto fromEntity(RoutineExerciseSets e){
        return RoutineExerciseSetsDto.builder()
                .id(e.getId())
                .routineExerciesId(e.getRoutineExercise().getId())
                .order(e.getOrder())
                .weight(e.getWeight())
                .rep(e.getRep())
                .restSec(e.getRestSec())
                .build();
    }
}
