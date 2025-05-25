package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.mapper.RoutineHistoryMapper;
import com.easyroutine.repository.routine_history.RoutineHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoutineHistoryService {

    // repository
    private final RoutineHistoryRepository routineHistoryRepository;

    // mapper
    private final RoutineHistoryMapper routineHistoryMapper;

    public RoutineHistoryService(RoutineHistoryRepository routineHistoryRepository,
                                RoutineHistoryMapper routineHistoryMapper) {
        this.routineHistoryRepository = routineHistoryRepository;
        this.routineHistoryMapper = routineHistoryMapper;
    }

    public int getExerciseTime(String date) {
        return 0;
    }

    public int getTotalVolume(String date) {
        return 0;
    }

    public List<RoutineHistory> getRoutineHistory(String date) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createRoutineHistory(List<RoutineHistoryDto> routineHistoryDtos, String memberId) {
        List<RoutineHistory> routineHistories = routineHistoryDtos.stream()
                .map(dto -> routineHistoryMapper.toEntity(dto, memberId))
                .collect(Collectors.toList());
        routineHistoryRepository.saveAll(routineHistories);
        return "Success";
    }
}
