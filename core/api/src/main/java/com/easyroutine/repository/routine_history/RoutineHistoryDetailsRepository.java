package com.easyroutine.repository.routine_history;

import com.easyroutine.domain.routine_history.RoutineHistoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineHistoryDetailsRepository extends JpaRepository<RoutineHistoryDetails, Long> {
}
