package com.easyroutine.domain.routine_history.mapper;

import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryDetails;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class RoutineHistoryDetailsMapper {

	public RoutineHistoryDetails toEntity(RoutineHistoryDetailsDto dto, RoutineHistory routineHistory) {
		return RoutineHistoryDetails.builder()
			.id(dto.getId())
			.order(dto.getOrder())
			.weight(dto.getWeight())
			.rep(dto.getRep())
			.refreshTime(dto.getRefreshTime())
			.exerciseTime(dto.getExerciseSec())
			.routineHistory(routineHistory)
			.build();
	}

}
