package com.easyroutine.domain.exercises;

import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.repository.exercises.ExercisesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ExerciseService {

    private final ExercisesRepository exerciseRepository;

    public ExerciseService(ExercisesRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<ExerciseDto> getExercises(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Exercise> exercises = exerciseRepository.findAllByCategory(type, pageable);

        return exercises.stream()
                .map(exercise -> ExerciseDto.of(exercise))
                .toList();
    }
}
