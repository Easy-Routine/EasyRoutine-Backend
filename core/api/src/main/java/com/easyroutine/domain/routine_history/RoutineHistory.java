package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.routine.Routine;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "routineHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineHistoryExercise> routineHistoryExercises = new ArrayList<>();

    @Builder
    private RoutineHistory(Long id, Routine routine, String routineName, LocalDate exerciseDate, int orderIndex, String color, int workoutTime, List<RoutineHistoryExercise> routineHistoryExercises) {
        this.id = id;
        this.routine = routine;
        this.routineName = routineName;
        this.exerciseDate = exerciseDate;
        this.orderIndex = orderIndex;
        this.color = color;
        this.workoutTime = workoutTime;
        this.routineHistoryExercises = routineHistoryExercises;
    }

    public void addRoutineExercises(List<RoutineHistoryExercise> routineHistoryExercises) {
        for (RoutineHistoryExercise routineHistoryExercise : routineHistoryExercises) {
            routineHistoryExercise.setRoutineHistory(this);
        }
        this.routineHistoryExercises = routineHistoryExercises;
    }

    public void updateFrom(RoutineHistory routineHistory) {

    }
}
