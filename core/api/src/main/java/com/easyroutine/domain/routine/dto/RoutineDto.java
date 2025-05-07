package com.easyroutine.domain.routine.dto;

import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDto {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String memberId;

    @NotBlank(message = "루틴 이름은 비어 있을 수 없습니다.")
    private String name;
    @NotBlank(message = "루틴 색은 비어 있을 수 없습니다.")
    private String color;
    @NotEmpty(message = "하나 이상의 루틴을 채워 주세요.")
    @Valid
    private List<@Valid RoutineExerciseDto> routineExerciseDtoList;


    public void setMemberIdFromToken(String memberId){
        this.memberId = memberId;
    }
}
