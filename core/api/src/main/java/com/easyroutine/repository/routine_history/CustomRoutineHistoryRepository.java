package com.easyroutine.repository.routine_history;

import com.easyroutine.domain.routine_history.RoutineHistory;

import java.time.LocalDate;
import java.util.List;

public interface CustomRoutineHistoryRepository {
    List<RoutineHistory> searchByMemberIdAndExerciseDate(String memberId, LocalDate date);
}
