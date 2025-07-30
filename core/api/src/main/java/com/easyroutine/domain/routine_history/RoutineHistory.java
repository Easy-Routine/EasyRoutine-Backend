package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.routine.Routine;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "routine_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "routine_id", nullable = false)
	private Routine routine;

	@Column(name = "routine_name", nullable = false)
	private String routineName;

	@Column(name = "exercise_date", nullable = false)
	private LocalDate exerciseDate;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	@Column(name = "color", nullable = false)
	private String color;

	@Column(name = "workout_time", nullable = false)
	private int workoutTime;

	@Column(name = "member_id", nullable = false)
	private String memberId;

	@OneToMany(mappedBy = "routineHistory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RoutineHistoryExercise> routineHistoryExercises = new HashSet<>();

	@Builder
	private RoutineHistory(Long id, Routine routine, String routineName, LocalDate exerciseDate, int orderIndex,
			String color, int workoutTime, Set<RoutineHistoryExercise> routineHistoryExercises, String memberId) {
		this.id = id;
		this.routine = routine;
		this.routineName = routineName;
		this.exerciseDate = exerciseDate;
		this.orderIndex = orderIndex;
		this.color = color;
		this.workoutTime = workoutTime;
		this.routineHistoryExercises = routineHistoryExercises;
		this.memberId = memberId;
	}

	public void addRoutineExercises(Set<RoutineHistoryExercise> routineHistoryExercises) {
		for (RoutineHistoryExercise routineHistoryExercise : routineHistoryExercises) {
			routineHistoryExercise.setRoutineHistory(this);
		}
		this.routineHistoryExercises = routineHistoryExercises;
	}

	public int getTotalWorkoutTime() {
		return routineHistoryExercises.stream()
			.flatMap(exercise -> exercise.getRoutineHistoryExerciseSets().stream())
			.mapToInt(RoutineHistoryExerciseSets::getExerciseTime)
			.sum();
	}

	public double getTotalWeightLifted() {
		return routineHistoryExercises.stream()
			.flatMap(exercise -> exercise.getRoutineHistoryExerciseSets().stream())
			.mapToDouble(RoutineHistoryExerciseSets::getWeight)
			.sum();
	}

}
