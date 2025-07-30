package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "routine_history_exercise_sets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineHistoryExerciseSets extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, optional = false)
	private RoutineHistoryExercise routineHistoryExercise;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	@Column(name = "reps", nullable = false)
	private int reps;

	@Column(name = "weight", nullable = false)
	private double weight;

	@Column(name = "exercise_time", nullable = false)
	private int exerciseTime; // in seconds

	@Column(name = "refresh_time", nullable = false)
	private int refreshTime; // in seconds

	@Builder
	private RoutineHistoryExerciseSets(Long id, RoutineHistoryExercise routineHistoryExercise, int orderIndex, int reps,
			double weight, int exerciseTime, int refreshTime) {
		this.id = id;
		this.routineHistoryExercise = routineHistoryExercise;
		this.orderIndex = orderIndex;
		this.reps = reps;
		this.weight = weight;
		this.exerciseTime = exerciseTime;
		this.refreshTime = refreshTime;
	}

	public void setRoutineHistoryExercise(RoutineHistoryExercise routineHistoryExercise) {
		if (this.routineHistoryExercise != null) {
			this.routineHistoryExercise.getRoutineHistoryExerciseSets().remove(this);
		}
		this.routineHistoryExercise = routineHistoryExercise;
		if (routineHistoryExercise != null) {
			routineHistoryExercise.getRoutineHistoryExerciseSets().add(this);
		}
	}

}
