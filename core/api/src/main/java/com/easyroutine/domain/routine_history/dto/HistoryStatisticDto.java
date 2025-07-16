package com.easyroutine.domain.routine_history.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryStatisticDto {
    private LocalDate date;
    private int totalWorkoutTime;
    private int totalWeightLifted;
    private int totalRepCount;
}
