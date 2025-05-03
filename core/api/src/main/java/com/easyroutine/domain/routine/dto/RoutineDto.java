package com.easyroutine.domain.routine.dto;

import com.easyroutine.domain.exercises.dto.ExercisesDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Getter
public class RoutineDto {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String memberId;

    private String name;
    private String color;
    private List<RoutineExerciseDto> routineExerciseDtoList;


    public void setMemberIdFromToken(String memberId){
        this.memberId = memberId;
    }
}
