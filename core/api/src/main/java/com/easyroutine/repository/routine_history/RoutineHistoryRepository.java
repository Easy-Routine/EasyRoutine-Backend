package com.easyroutine.repository.routine_history;

import com.easyroutine.domain.routine_history.RoutineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineHistoryRepository extends JpaRepository<RoutineHistory, Long>, CustomRoutineHistoryRepository {
}
