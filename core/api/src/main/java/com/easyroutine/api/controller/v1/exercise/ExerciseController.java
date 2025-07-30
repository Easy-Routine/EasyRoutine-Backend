package com.easyroutine.api.controller.v1.exercise;

import com.easyroutine.api.controller.v1.exercise.request.ExerciseCreateRequest;
import com.easyroutine.api.controller.v1.exercise.request.ExerciseDeleteRequest;
import com.easyroutine.api.controller.v1.exercise.request.ExerciseUpdateRequest;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseService;
import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.global.response.PageData;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "운동 API", description = "운동 관련 API")
@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

	private final ExerciseService exerciseService;

	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@Operation(summary = "운동 목록 조회", description = "운동 목록을 조회합니다.")
	@GetMapping
	public PageData<ExerciseDto> getExercises(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size,
			@RequestParam(name = "keyword", required = false) String keyword,
			@AuthenticationPrincipal CustomOAuth2User user) {
		String memberId = user.getMemberId();
		List<Exercise> exercises = exerciseService.getExercises(category, page - 1, size, keyword, memberId);
		List<ExerciseDto> exerciseDtos = exercises.stream().map(ExerciseDto::of).toList();
		return PageData.of(exerciseDtos.size(), exerciseDtos);
	}

	@Operation(summary = "운동 상세 조회", description = "운동의 상세 정보를 조회합니다.")
	@GetMapping("/{id}")
	public ExerciseDto getExercise(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User user) {
		String memberId = user.getMemberId();
		Exercise exercise = exerciseService.getExercise(id, memberId);
		return ExerciseDto.of(exercise);
	}

	@Operation(summary = "운동 생성", description = "운동을 생성합니다.")
	@PostMapping
	public String createExercise(@Valid @RequestBody ExerciseCreateRequest request,
			@AuthenticationPrincipal CustomOAuth2User user) {
		String memberId = user.getMemberId();
		ExerciseDto exerciseDto = ExerciseDto.of(request);

		return exerciseService.createExercise(exerciseDto, memberId);
	}

	@Operation(summary = "운동 수정", description = "운동을 수정합니다.")
	@PutMapping
	public String updateExercise(@RequestBody ExerciseUpdateRequest request,
			@AuthenticationPrincipal CustomOAuth2User user) {
		String memberId = user.getMemberId();
		ExerciseDto exerciseDto = ExerciseDto.of(request);

		return exerciseService.updateExercise(exerciseDto, memberId);
	}

	@Operation(summary = "운동 삭제", description = "운동을 삭제합니다.")
	@DeleteMapping
	public String deleteExercise(@RequestBody ExerciseDeleteRequest request,
			@AuthenticationPrincipal CustomOAuth2User user) {
		String memberId = user.getMemberId();
		return exerciseService.deleteExercise(request.getId(), memberId);
	}

}
