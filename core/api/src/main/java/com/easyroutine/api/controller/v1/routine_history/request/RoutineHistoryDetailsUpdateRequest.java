package com.easyroutine.api.controller.v1.routine_history.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoutineHistoryDetailsUpdateRequest {

    @Schema(description = "상세 세트 아이디", example = "1")
    private Long id;

    @Schema(description = "순서", example = "1")
    private int order;

    @Schema(description = "무게", example = "20.0")
    private double weight;

    @Schema(description = "운동 시간", example = "60")
    private int exerciseSec;

    @Schema(description = "횟수", example = "10")
    private int rep;

    @Schema(description = "휴식 시간", example = "30")
    private int restSec;
}