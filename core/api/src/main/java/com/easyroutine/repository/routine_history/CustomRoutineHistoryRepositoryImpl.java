package com.easyroutine.repository.routine_history;

import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.dto.HistoryStatisticDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.easyroutine.domain.routine.QRoutine.routine;
import static com.easyroutine.domain.routine_history.QRoutineHistory.routineHistory;
import static com.easyroutine.domain.routine_history.QRoutineHistoryExercise.routineHistoryExercise;
import static com.easyroutine.domain.routine_history.QRoutineHistoryExerciseSets.routineHistoryExerciseSets;

@Repository
@RequiredArgsConstructor
public class CustomRoutineHistoryRepositoryImpl implements CustomRoutineHistoryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RoutineHistory> searchByMemberIdAndExerciseDate(String memberId, LocalDate date) {
        return  queryFactory
                .select(routineHistory)
                .from(routineHistory)
                .leftJoin(routineHistory.routine, routine).fetchJoin()
                .leftJoin(routineHistory.routineHistoryExercises, routineHistoryExercise).fetchJoin()
                .leftJoin(routineHistoryExercise.routineHistoryExerciseSets, routineHistoryExerciseSets).fetchJoin()
                .where(
                        routineHistory.memberId.eq(memberId),
                        (routineHistory.exerciseDate.eq(date))
                )
                .orderBy(routineHistory.orderIndex.asc())
                .fetch();
    }

    @Override
    public List<HistoryStatisticDto> searchStatisticsByExerciseId(Long exerciseId, String memberId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(
                        HistoryStatisticDto.class,
                        routineHistory.exerciseDate,
                        routineHistoryExerciseSets.exerciseTime.sum().as("totalExerciseTime"),
                        routineHistoryExerciseSets.reps.multiply(routineHistoryExerciseSets.weight).sum().as("totalVolume"),
                        routineHistoryExerciseSets.reps.sum().as("totalReps")
                        ))
                .from(routineHistoryExerciseSets)
                .join(routineHistoryExerciseSets.routineHistoryExercise, routineHistoryExercise)
                .join(routineHistoryExercise.routineHistory, routineHistory)
                .where(
                        routineHistoryExercise.exercise.id.eq(exerciseId),
                        routineHistory.memberId.eq(memberId),
                        routineHistory.exerciseDate.between(startDate, endDate)
                )
                .groupBy(routineHistory.exerciseDate)
                .fetch();
    }
}
