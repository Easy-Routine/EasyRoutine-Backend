package com.easyroutine.repository.routine_exercise_sets;

import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineExerciseSetsRepository extends JpaRepository<RoutineExerciseSets, Long> {
}
