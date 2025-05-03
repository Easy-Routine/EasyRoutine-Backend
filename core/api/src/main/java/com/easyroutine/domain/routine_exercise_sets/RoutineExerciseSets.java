package com.easyroutine.domain.routine_exercise_sets;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.routine_exercise.RoutineExercise;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "routine_exercise_sets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineExerciseSets extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_exercise_id", nullable = false)
    private RoutineExercise routineExercise;

    @Column(name="order_index" ,nullable = false)
    private int order;

    @Column
    private Double weight;

    @Column
    private int rep;

    @Column
    private String refreshTime; // mm:ss

}
