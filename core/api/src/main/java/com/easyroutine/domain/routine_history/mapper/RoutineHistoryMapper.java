package com.easyroutine.domain.routine_history.mapper;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.routine.Routine;
import com.easyroutine.domain.routine_history.RoutineHistory;
import com.easyroutine.domain.routine_history.RoutineHistoryExercise;
import com.easyroutine.domain.routine_history.RoutineHistoryExerciseSets;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistoryExerciseDto;
import com.easyroutine.domain.routine_history.dto.RoutineHistorySetsDto;
import com.easyroutine.repository.exercises.ExercisesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoutineHistoryMapper {

    private final ExercisesRepository exercisesRepository;

    public RoutineHistory toEntity(RoutineHistoryDto dto) {
        Routine routine = Routine.of(dto.getRoutineId());
        Map<Long, Exercise> exerciseMap = getExercisesMapBy(dto);

        RoutineHistory routineHistory = RoutineHistory.builder()
                .routine(routine)
                .routineName(dto.getName())
                .color(dto.getColor())
                .orderIndex(dto.getOrder())
                .workoutTime(dto.getWorkoutTime())
                .routineHistoryExercises(new ArrayList<>())
                .build();

        List<RoutineHistoryExercise> routineHistoryExercises = dto.getRoutineExercises().stream()
                .map(exerciseDto -> {
                    RoutineHistoryExercise routineHistoryExercise = toEntity(exerciseDto, exerciseMap);
                    routineHistoryExercise.setRoutineHistory(routineHistory);
                    return routineHistoryExercise;
                })
                .toList();

        routineHistory.addRoutineExercises(routineHistoryExercises);

        return routineHistory;
    }

    public RoutineHistoryExercise toEntity(RoutineHistoryExerciseDto dto, Map<Long, Exercise> exerciseMap) {
        Exercise exercise = exerciseMap.getOrDefault(dto.getExerciseId(), Exercise.of(dto.getExerciseId()));

        RoutineHistoryExercise routineHistoryExercise = RoutineHistoryExercise.builder()
                .routineHistory(null)
                .orderIndex(dto.getOrder())
                .exercise(exercise)
                .exerciseName(exercise.getName())
                .exerciseType(new ArrayList<>(exercise.getTypes()))
                .exerciseCategories(exercise.getCategory())
                .routineHistoryExerciseSets(new ArrayList<>())
                .build();

        List<RoutineHistoryExerciseSets> sets = dto.getSets().stream()
                .map(setDto -> {
                    RoutineHistoryExerciseSets routineHistoryExerciseSets = toEntity(setDto);
                    routineHistoryExerciseSets.setRoutineHistoryExercise(routineHistoryExercise);
                    return routineHistoryExerciseSets;
                })
                .toList();

        routineHistoryExercise.addRoutineHistoryExerciseSets(sets);

        return routineHistoryExercise;
    }

    public RoutineHistoryExerciseSets toEntity(RoutineHistorySetsDto routineHistorySetsDto) {
        return RoutineHistoryExerciseSets.builder()
                .routineHistoryExercise(null)
                .orderIndex(routineHistorySetsDto.getOrder())
                .reps(routineHistorySetsDto.getReps())
                .weight(routineHistorySetsDto.getWeight())
                .exerciseTime(routineHistorySetsDto.getExerciseTime())
                .refreshTime(routineHistorySetsDto.getRestSec())
                .build();
    }

    private Map<Long, Exercise> getExercisesMapBy(RoutineHistoryDto dto) {
        List<Long> exerciseIds = dto.getRoutineExercises().stream()
                .map(RoutineHistoryExerciseDto::getExerciseId)
                .toList();

        List<Exercise> exercises = exercisesRepository.findAllById(exerciseIds);

        return exercises.stream()
                .collect(Collectors.toMap(Exercise::getId, e -> e));
    }
}
