package com.easyroutine.api.controller.v1.routine.request;

import com.easyroutine.domain.routine.dto.RoutineDto;
import lombok.Getter;

@Getter
public class RoutineCreateRequest {
    private RoutineDto routineDto;
}