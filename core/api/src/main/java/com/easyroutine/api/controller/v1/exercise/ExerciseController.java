package com.easyroutine.api.controller.v1.exercise;

import com.easyroutine.api.controller.v1.exercise.request.ExerciseCreateRequest;
import com.easyroutine.api.controller.v1.exercise.request.ExerciseUpdateRequest;
import com.easyroutine.domain.exercises.ExerciseService;
import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.global.response.PageData;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import com.easyroutine.infrastructure.s3.S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/{type}/{page}/{size}")
    public PageData<?> getExercises(@PathVariable String type, @PathVariable int page, @PathVariable int size) {
        List<ExerciseDto> exercises = exerciseService.getExercises(type, page, size);
        return PageData.of(0, exercises);
    }

    @PostMapping(consumes = "multipart/form-data")
    public String createExercise(@RequestParam("image") MultipartFile image,
                                @RequestParam("request") ExerciseCreateRequest request,
                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        String imageUrl = uploadImageAndGetImageUrl(image);
        ExerciseDto exerciseDto = ExerciseDto.of(request, imageUrl);

        return exerciseService.createExercise(exerciseDto, memberId);
    }

    @PutMapping
    public String updateExercise(@RequestParam("image") MultipartFile image,
                                @RequestParam("request") ExerciseUpdateRequest request,
                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        String imageUrl = uploadImageAndGetImageUrl(image);
        ExerciseDto exerciseDto = ExerciseDto.of(request, imageUrl);

        return exerciseService.updateExercise(exerciseDto, memberId);
    }

    @DeleteMapping
    public String deleteExercise(@RequestBody Long id,
                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String memberId = customOAuth2User.getMemberId();
        return exerciseService.deleteExercise(id, memberId);
    }

    private String uploadImageAndGetImageUrl(MultipartFile image) {
        if(image.isEmpty()) {
            return null;
        }
        return s3Service.uploadFile(image, uploadDirectoryPath);
    }
}
