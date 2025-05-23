package com.easyroutine.domain.routine;

import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.RoutineExercise;
import com.easyroutine.domain.routine_exercise.RoutineExerciseMapper;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSetsMapper;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.easyroutine.repository.routine.RoutineRepository;
import com.easyroutine.repository.routine_exercise.RoutineExerciseRepository;
import com.easyroutine.repository.routine_exercise_sets.RoutineExerciseSetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineMapper routineMapper;
    private final RoutineExerciseMapper routineExerciseMapper;
    private final RoutineExerciseSetsMapper routineExerciseSetsMapper;

    private final RoutineRepository routineRepository;
    private final RoutineExerciseRepository routineExerciseRepository;
    private final RoutineExerciseSetsRepository routineExerciseSetsRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long createRoutine(RoutineDto routineDto){
        Routine routine = routineRepository.save(routineMapper.toEntity(routineDto));

        for(RoutineExerciseDto routineExerciseDto : routineDto.getRoutineExerciseDtoList()){
            routineExerciseDto.setRoutineId(routine.getId());
            RoutineExercise routineExercise = routineExerciseRepository.save(routineExerciseMapper.toEntity(routineExerciseDto));

            for(RoutineExerciseSetsDto routineExerciseSetsDto : routineExerciseDto.getSetsDtoList()){
                routineExerciseSetsDto.setRoutineExerciesId(routineExercise.getId());
                routineExerciseSetsRepository.save(routineExerciseSetsMapper.toEntity(routineExerciseSetsDto));
            }
        }

        return routine.getId();
    }

}
