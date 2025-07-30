package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.domain.routine_history.RoutineHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorySummaryDto {

	@Schema(description = "총 운동 시간", example = "5760")
	private int totalWorkoutTime;

	@Schema(description = "총 운동 볼륨", example = "3450")
	private double totalworkoutWeight;

	public static HistorySummaryDto of(List<RoutineHistory> histories) {
		int totalWorkoutTime = histories.stream().mapToInt(h -> h.getTotalWorkoutTime()).sum();

		double totalWeights = histories.stream().mapToDouble(h -> h.getTotalWeightLifted()).sum();

		return HistorySummaryDto.builder().totalWorkoutTime(totalWorkoutTime).totalworkoutWeight(totalWeights).build();
	}

}
