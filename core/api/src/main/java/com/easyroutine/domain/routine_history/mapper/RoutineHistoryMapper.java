package com.easyroutine.domain.routine_history.mapper;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.Routine;
import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryDetails;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoutineHistoryMapper {

    private final RoutineHistoryDetailsMapper routineHistoryDetailsMapper;

    public RoutineHistoryMapper(RoutineHistoryDetailsMapper routineHistoryDetailsMapper) {
        this.routineHistoryDetailsMapper = routineHistoryDetailsMapper;
    }

    public RoutineHistory toEntity(RoutineHistoryDto dto, String memberId) {
        RoutineHistory routineHistory = RoutineHistory.builder()
                .id(dto.getId())
                .exerciseDate(dto.getExerciseDate())
                .order(dto.getOrder())
                .routine(Routine.of(dto.getRoutineId()))
                .exercise(Exercise.of(dto.getExerciseId()))
                .member(Member.of(memberId))
                .build();

        List<RoutineHistoryDetails> routineHistoryDetails = dto.getRoutineHistoryDetails().stream()
                .map(detail -> routineHistoryDetailsMapper.toEntity(detail, routineHistory))
                .toList();

        routineHistory.addRoutineHistoryDetails(routineHistoryDetails);

        return routineHistory;
    }
}
