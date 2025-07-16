package com.easyroutine.repository.routine_history;

import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.dto.HistoryStatisticDto;

import java.time.LocalDate;
import java.util.List;

public interface CustomRoutineHistoryRepository {
    List<RoutineHistory> searchByMemberIdAndExerciseDate(String memberId, LocalDate date);

    List<HistoryStatisticDto> searchStatisticsByExerciseId(Long exerciseId, String memberId, LocalDate startDate, LocalDate endDate);
}
