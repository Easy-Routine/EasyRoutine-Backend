package com.easyroutine.domain.routine_exercise;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.routine.Routine;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "routine_exercise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineExercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "order", nullable = false)
    private int order;



}
