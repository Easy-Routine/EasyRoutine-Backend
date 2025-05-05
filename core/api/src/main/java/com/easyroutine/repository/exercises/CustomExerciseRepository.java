package com.easyroutine.repository.exercises;

import com.easyroutine.domain.exercises.Exercise;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomExerciseRepository {

    List<Exercise> findAllByCategory(String types, Pageable pageable);
}
