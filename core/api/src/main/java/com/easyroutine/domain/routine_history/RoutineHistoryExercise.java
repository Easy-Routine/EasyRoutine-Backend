package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.ExerciseType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "routine_history_exercise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineHistoryExercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoutineHistory routineHistory;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Exercise exercise;

    @Column(name = "exercise_name")
    private String exerciseName;

    @ElementCollection
    @Column(name = "exercise_type")
    @Enumerated(EnumType.STRING)
    private List<ExerciseType> exerciseType = List.of();

    @Column(name = "exercise_category")
    private ExerciseCategory exerciseCategories;

    @OneToMany(mappedBy = "routineHistoryExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoutineHistoryExerciseSets> routineHistoryExerciseSets = new HashSet<>();

    @Builder
    private RoutineHistoryExercise(
            Long id,
            RoutineHistory routineHistory,
            int orderIndex,
            Exercise exercise,
            String exerciseName,
            List<ExerciseType> exerciseType,
            ExerciseCategory exerciseCategories,
            Set<RoutineHistoryExerciseSets> routineHistoryExerciseSets
    ) {
        this.id = id;
        this.routineHistory = routineHistory;
        this.orderIndex = orderIndex;
        this.exercise = exercise;
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.exerciseCategories = exerciseCategories;
        this.routineHistoryExerciseSets = routineHistoryExerciseSets;
    }

    public void setRoutineHistory(RoutineHistory routineHistory) {
        if (this.routineHistory != null) {
            this.routineHistory.getRoutineHistoryExercises().remove(this);
        }
        this.routineHistory = routineHistory;
        if (routineHistory != null) {
            routineHistory.getRoutineHistoryExercises().add(this);
        }
    }

    public void addRoutineHistoryExerciseSets(Set<RoutineHistoryExerciseSets> sets) {
        for (RoutineHistoryExerciseSets set : sets) {
            set.setRoutineHistoryExercise(this);
        }
        this.routineHistoryExerciseSets = sets;
    }
}
