package com.easyroutine.domain.routine_history.dto;

import com.easyroutine.domain.routine_history.RoutineHistory;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRoutineHistoryDto {

	private Long id;

	private Long routineId;

	private String name;

	private String color;

	private LocalDate exerciseDate;

	private int order;

	private int workoutTime;

	public static MonthlyRoutineHistoryDto createByRoutineHistory(RoutineHistory routineHistory) {
		return MonthlyRoutineHistoryDto.builder()
			.id(routineHistory.getId())
			.routineId(routineHistory.getRoutine().getId())
			.name(routineHistory.getRoutineName())
			.color(routineHistory.getColor())
			.exerciseDate(routineHistory.getExerciseDate())
			.order(routineHistory.getOrderIndex())
			.workoutTime(routineHistory.getWorkoutTime())
			.build();
	}

}
