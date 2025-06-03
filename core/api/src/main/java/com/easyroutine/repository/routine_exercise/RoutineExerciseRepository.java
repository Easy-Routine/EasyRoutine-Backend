package com.easyroutine.repository.routine_exercise;

import com.easyroutine.domain.routine_exercise.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
    void deleteByRoutineId(Long id);
}
