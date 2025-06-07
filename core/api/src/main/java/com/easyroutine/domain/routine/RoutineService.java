package com.easyroutine.domain.routine;

import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.RoutineExercise;
import com.easyroutine.domain.routine_exercise.RoutineExerciseMapper;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSets;
import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSetsMapper;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.easyroutine.global.exception.DataException;
import com.easyroutine.global.response.ResultType;
import com.easyroutine.repository.routine.RoutineRepository;
import com.easyroutine.repository.routine_exercise.RoutineExerciseRepository;
import com.easyroutine.repository.routine_exercise_sets.RoutineExerciseSetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Long createRoutine(RoutineDto routineDto) {
        Routine routine = routineRepository.save(routineMapper.toEntity(routineDto));

        for (RoutineExerciseDto routineExerciseDto : routineDto.getRoutineExerciseDtoList()) {
            routineExerciseDto.setRoutineId(routine.getId());
            RoutineExercise routineExercise = routineExerciseRepository.save(routineExerciseMapper.toEntity(routineExerciseDto));

            for (RoutineExerciseSetsDto routineExerciseSetsDto : routineExerciseDto.getSetsDtoList()) {
                routineExerciseSetsDto.setRoutineExerciesId(routineExercise.getId());
                routineExerciseSetsRepository.save(routineExerciseSetsMapper.toEntity(routineExerciseSetsDto));
            }
        }

        return routine.getId();
    }


    /**
     * 루틴 정보 조회
     * @param member
     * @return
     */
    public List<RoutineDto> findAllRoutine(Member member) {
        List<Routine> routineList = routineRepository.findWithExercisesByMember(member);
        return routineList.stream().map(routineMapper::fromEntity).toList();
    }


    public RoutineDto deleteRoutine(Long id, Member member) {
        Routine routine = routineRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new DataException(
                        ResultType.DATA_NOT_FOUND, "루틴을 찾을 수 없습니다."));

        routine.setDeletedAt();

        for(RoutineExercise exercise : routine.getRoutineExercises()){
            exercise.setDeletedAt();

            for(RoutineExerciseSets set : exercise.getSets()){
                set.setDeletedAt();
            }
        }
        // 삭제된 엔티티를 DTO로 변환해 리턴z
        return routineMapper.fromEntity(routine);
    }

}
