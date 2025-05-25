package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "routine_history_details")
@NoArgsConstructor(access = PROTECTED)
public class RoutineHistoryDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_index", nullable = false)
    private int order;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "rep")
    private int rep;

    @Column(name = "refresh_time")
    private int refreshTime;

    @Column(name = "exercise_time")
    private int exerciseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_history_id", nullable = false)
    private RoutineHistory routineHistory;

    @Builder
    public RoutineHistoryDetails(Long id, int order, Double weight, int rep, int refreshTime, int exerciseTime, RoutineHistory routineHistory) {
        this.id = id;
        this.order = order;
        this.weight = weight;
        this.rep = rep;
        this.refreshTime = refreshTime;
        this.exerciseTime = exerciseTime;
        this.routineHistory = routineHistory;
    }
}
