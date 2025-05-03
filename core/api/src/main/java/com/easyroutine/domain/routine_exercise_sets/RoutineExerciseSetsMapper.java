package com.easyroutine.domain.routine_exercise_sets;

import com.easyroutine.domain.routine.Routine;
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
                .refreshTime(routineExerciseSetsDto.getRefreshTime())
                .build();
    }
}
