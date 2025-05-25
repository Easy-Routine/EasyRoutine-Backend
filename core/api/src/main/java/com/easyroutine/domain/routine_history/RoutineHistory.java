package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.Routine;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "routine_history")
@NoArgsConstructor(access = PROTECTED)
public class RoutineHistory extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exercise_date")
    private LocalDateTime exerciseDate;

    @Column(name = "order_index")
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "routineHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineHistoryDetails> routineHistoryDetails = new ArrayList<>();

    @Builder
    private RoutineHistory(Long id, LocalDateTime exerciseDate, int order, Routine routine, Exercise exercise, Member member, List<RoutineHistoryDetails> routineHistoryDetails) {
        this.id = id;
        this.exerciseDate = exerciseDate;
        this.order = order;
        this.routine = routine;
        this.exercise = exercise;
        this.member = member;
        this.routineHistoryDetails = routineHistoryDetails;
    }

    public static RoutineHistory of(RoutineHistoryDto routineHistoryDto) {
        return RoutineHistory.builder()
                .id(routineHistoryDto.getId())
                .exerciseDate(routineHistoryDto.getExerciseDate())
                .order(routineHistoryDto.getOrder())
                .routine(Routine.of(routineHistoryDto.getRoutineId()))
                .exercise(Exercise.of(routineHistoryDto.getExerciseId()))
                .member(Member.of(routineHistoryDto.getMemberId()))
                .build();
    }

    public void addRoutineHistoryDetails(List<RoutineHistoryDetails> routineHistoryDetails) {
        this.routineHistoryDetails = routineHistoryDetails;
    }
}
