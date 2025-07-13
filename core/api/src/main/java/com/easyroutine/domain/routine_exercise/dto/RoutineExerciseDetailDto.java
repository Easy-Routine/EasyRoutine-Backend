package com.easyroutine.domain.routine_exercise.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseDetailDto {
    private Long id;
    private String imageUrl;
    private String name;
}
