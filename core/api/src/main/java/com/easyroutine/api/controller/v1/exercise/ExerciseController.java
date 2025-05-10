package com.easyroutine.api.controller.v1.exercise;

import com.easyroutine.api.controller.v1.exercise.request.ExerciseCreateRequest;
import com.easyroutine.api.controller.v1.exercise.request.ExerciseDeleteRequest;
import com.easyroutine.api.controller.v1.exercise.request.ExerciseUpdateRequest;
import com.easyroutine.domain.exercises.ExerciseService;
import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.global.response.PageData;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import com.easyroutine.infrastructure.s3.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "운동 API", description = "운동 관련 API")
@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    private final String uploadDirectoryPath;
    private final S3Service s3Service;
    private final ExerciseService exerciseService;

    public ExerciseController(@Value("${cloud.s3.directory}") String uploadDirectoryPath,
                                S3Service s3Service,
                                ExerciseService exerciseService) {
        this.uploadDirectoryPath = uploadDirectoryPath;
        this.s3Service = s3Service;
        this.exerciseService = exerciseService;
    }

    @Operation(summary = "운동 목록 조회", description = "운동 목록을 조회합니다.")
    @GetMapping("/{type}/{page}/{size}")
    public PageData<?> getExercises(@PathVariable String type, @PathVariable int page, @PathVariable int size) {
        List<ExerciseDto> exercises = exerciseService.getExercises(type, page, size);
        return PageData.of(0, exercises);
    }

    @Operation(summary = "운동 생성", description = "운동을 생성합니다.")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String createExercise(@RequestPart(value ="image", required = false)  MultipartFile image,
                                @RequestPart(value = "request") ExerciseCreateRequest request,
                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        String imageUrl = uploadImageAndGetImageUrl(image);
        ExerciseDto exerciseDto = ExerciseDto.of(request, imageUrl);

        return exerciseService.createExercise(exerciseDto, memberId);
    }

    @Operation(summary = "운동 수정", description = "운동을 수정합니다.")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String updateExercise(@RequestPart(value = "image", required = false) MultipartFile image,
                                @RequestPart(value = "request") ExerciseUpdateRequest request,
                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        String imageUrl = uploadImageAndGetImageUrl(image);
        ExerciseDto exerciseDto = ExerciseDto.of(request, imageUrl);

        return exerciseService.updateExercise(exerciseDto, memberId);
    }

    @Operation(summary = "운동 삭제", description = "운동을 삭제합니다.")
    @DeleteMapping
    public String deleteExercise(@RequestBody ExerciseDeleteRequest request,
                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        return exerciseService.deleteExercise(request.getId(), memberId);
    }

    private String uploadImageAndGetImageUrl(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        return s3Service.uploadFile(image, uploadDirectoryPath);
    }
}
