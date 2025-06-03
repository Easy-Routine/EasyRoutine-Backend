package com.easyroutine.repository.routine_history;

import com.easyroutine.domain.routine_history.RoutineHistory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class RoutineHistoryQueryRepository {

    private final EntityManager em;

    public RoutineHistoryQueryRepository(EntityManager em) {
        this.em = em;
    }

    public int getExerciseTime(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = parsedDate.atStartOfDay();
        LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);

        return em.createQuery(
                        "SELECT COALESCE(SUM(d.exerciseTime), 0) " +
                                "FROM RoutineHistoryDetails d " +
                                "JOIN d.routineHistory rh " +
                                "WHERE rh.exerciseDate BETWEEN :start AND :end", Long.class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getSingleResult().intValue();
    }

    public int getTotalVolume(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = parsedDate.atStartOfDay();
        LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);

        return em.createQuery(
                        "SELECT COALESCE(SUM(d.weight), 0) " +
                                "FROM RoutineHistoryDetails d " +
                                "JOIN d.routineHistory rh " +
                                "WHERE rh.exerciseDate BETWEEN :start AND :end", Double.class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getSingleResult().intValue();
    }

    public List<RoutineHistory> findAllByExerciseDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = parsedDate.atStartOfDay();
        LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);

        return em.createQuery(
                        "SELECT rh FROM RoutineHistory rh JOIN FETCH rh.routineHistoryDetails WHERE rh.exerciseDate BETWEEN :start AND :end", RoutineHistory.class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getResultList();
    }

    public Optional<RoutineHistory> findAllByRoutineHistoryId(Long id) {
        RoutineHistory result = em.createQuery(
                        "SELECT rh FROM RoutineHistory rh JOIN FETCH rh.routineHistoryDetails WHERE rh.id = :id", RoutineHistory.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.of(result);
    }
}

