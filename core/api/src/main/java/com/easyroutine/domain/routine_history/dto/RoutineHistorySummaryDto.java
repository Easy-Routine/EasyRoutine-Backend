package com.easyroutine.domain.routine_history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineHistorySummaryDto {

	int exerciseTime;

	int totalVolume;

	public static RoutineHistorySummaryDto of(int exerciseTime, int totalVolume) {
		return RoutineHistorySummaryDto.builder().exerciseTime(exerciseTime).totalVolume(totalVolume).build();
	}

}
