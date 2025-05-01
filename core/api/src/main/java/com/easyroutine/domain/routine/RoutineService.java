package com.easyroutine.domain.routine;

import com.easyroutine.api.controller.v1.routine.request.RoutineCreateRequest;
import com.easyroutine.domain.exercises.dto.ExercisesDto;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.RoutineExercise;
import com.easyroutine.domain.routine_exercise.RoutineExerciseMapper;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSetsMapper;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.easyroutine.repository.routine.RoutineRepository;
import com.easyroutine.repository.routine_exercise.RoutineExerciseRepository;
import com.easyroutine.repository.routine_exercise_sets.RoutineExerciseSetsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
            RoutineExercise routineExercise = routineExerciseRepository.save(routineExerciseMapper.toEntity(routineExerciseDto));

            for(RoutineExerciseSetsDto routineExerciseSetsDto : routineExerciseDto.getSetsDtoList()){
                routineExerciseSetsRepository.save(routineExerciseSetsMapper.toEntity(routineExerciseSetsDto));
            }
        }

        return routine.getId();
    }

}
