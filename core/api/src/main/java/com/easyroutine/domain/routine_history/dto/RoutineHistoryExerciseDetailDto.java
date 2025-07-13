package com.easyroutine.domain.routine_history.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryExerciseDetailDto {

    private Long id;

    private String imageUrl;

    public static RoutineHistoryExerciseDetailDto createOf(Long id, String imageUrl) {
        return RoutineHistoryExerciseDetailDto.builder()
                .id(id)
                .imageUrl(imageUrl)
                .build();
    }
}
