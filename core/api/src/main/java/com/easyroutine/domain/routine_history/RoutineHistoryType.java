package com.easyroutine.domain.routine_history;

import com.querydsl.core.types.dsl.NumberExpression;

public enum RoutineHistoryType {

	WEIGHT, COUNT, TIME;

	public NumberExpression<Integer> getExpression(QRoutineHistoryExerciseSets sets) {
		return switch (this) {
			case WEIGHT -> sets.reps.multiply(sets.weight).sum();
			case COUNT -> sets.reps.sum();
			case TIME -> sets.exerciseTime.sum();
		};
	}

}
