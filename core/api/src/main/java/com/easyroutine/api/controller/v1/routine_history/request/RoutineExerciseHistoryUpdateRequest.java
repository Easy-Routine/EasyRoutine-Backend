package com.easyroutine.api.controller.v1.routine_history.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoutineExerciseHistoryUpdateRequest {

    @Schema(description = "운동 아이디", example = "1")
    private long exerciseId;

    @Schema(description = "운동 순서", example = "1")
    private int order;

    @Schema(description = "운동 이력 기록 리스트")
    private List<RoutineHistoryDetailsUpdateRequest> routineHistoryDetails;
}
