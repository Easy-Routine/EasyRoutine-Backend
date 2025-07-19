package com.easyroutine.domain.routine_history.service;

import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryPeriod;
import com.easyroutine.domain.routine_history.RoutineHistoryType;
import com.easyroutine.domain.routine_history.dto.HistoryStatisticDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.mapper.RoutineHistoryMapper;
import com.easyroutine.repository.routine_history.RoutineHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RoutineHistoryService {

    private final RoutineHistoryRepository routineHistoryRepository;
    private final RoutineHistoryMapper routineHistoryMapper;

    public RoutineHistoryService(
            RoutineHistoryRepository routineHistoryRepository,
            RoutineHistoryMapper routineHistoryMapper
    ) {
        this.routineHistoryRepository = routineHistoryRepository;
        this.routineHistoryMapper = routineHistoryMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long createRoutineHistory(RoutineHistoryDto routineHistoryDto, String memberId) {
        RoutineHistory routineHistory = routineHistoryMapper.toEntity(routineHistoryDto, memberId);
        RoutineHistory savedHistory = routineHistoryRepository.save(routineHistory);
        return savedHistory.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long deleteRoutineHistory(Long id, String memberId) {
        RoutineHistory routineHistory = routineHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Routine history not found with id: " + id));
        routineHistoryRepository.delete(routineHistory);
        return routineHistory.getId();
    }

    public List<RoutineHistory> getRoutineHistories(String memberId, String date) {
        return routineHistoryRepository.searchByMemberIdAndExerciseDate(memberId, LocalDate.parse(date));
    }

    public RoutineHistory getRoutineHistory(Long historyId) {
        return routineHistoryRepository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("Routine history not found with id: " + historyId));
    }

    public List<HistoryStatisticDto> getRoutineStatistics(
        Long exerciseId, String memberId, RoutineHistoryPeriod period, RoutineHistoryType type
) {
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = period.toStartDate(endDate);

    return routineHistoryRepository.searchStatisticsByExerciseId(
            exerciseId, memberId, startDate, endDate, type
    );
}

}
