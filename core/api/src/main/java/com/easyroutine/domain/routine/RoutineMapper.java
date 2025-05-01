package com.easyroutine.domain.routine;

import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.dto.RoutineDto;
import org.springframework.stereotype.Component;

@Component
public class RoutineMapper {

    public Routine toEntity(RoutineDto routineDto){
        return Routine.builder()
                .member(Member.of(routineDto.getMemberId()))
                .name(routineDto.getName())
                .color(routineDto.getColor())
                .build();

    }
}
