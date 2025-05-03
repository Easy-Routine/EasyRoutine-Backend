package com.easyroutine.api.controller.v1.routine.request;

import com.easyroutine.domain.routine.dto.RoutineDto;
import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class RoutineCreateRequest {
    @Valid
    private RoutineDto routineDto;
}