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

    private int order;


    public static RoutineHistoryExerciseDetailDto createOf(Long id, String imageUrl, int order) {
        return RoutineHistoryExerciseDetailDto.builder()
                .id(id)
                .imageUrl(imageUrl)
                .order(order)
                .build();
    }
}
