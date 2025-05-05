package com.easyroutine.api.controller.v1.exercise;

import com.easyroutine.domain.exercises.ExerciseService;
import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.global.response.PageData;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "운동 API", description = "운동 관련 API")
@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/{type}/{page}/{size}")
    public PageData<?> getExercises(@PathVariable String type, @PathVariable int page, @PathVariable int size) {
        List<ExerciseDto> exercises = exerciseService.getExercises(type, page, size);
        return PageData.of(0, exercises);
    }

    @PostMapping
    public String createExercise(){
        return "운동 생성";
    }

    @PutMapping
    public String updateExercise(){
        return "운동 수정";
    }

    @DeleteMapping
    public String deleteExercise(){
        return "운동 삭제";
    }
}
