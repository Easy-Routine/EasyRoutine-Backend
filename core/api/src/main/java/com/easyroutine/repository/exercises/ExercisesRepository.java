package com.easyroutine.repository.exercises;


import com.easyroutine.domain.exercises.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Long>, CustomExerciseRepository {
}
