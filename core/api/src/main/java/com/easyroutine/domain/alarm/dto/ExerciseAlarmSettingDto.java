package com.easyroutine.domain.alarm.dto;

import lombok.Getter;

import java.time.LocalTime;


public class ExerciseAlarmSettingDto {

    @Getter
    public static class ReqSaveExerciseAlarmSetting {
         private long memberId;
         private LocalTime exerciseStartTime;
         private Integer preAlarmMinutes;
         private Boolean alarmYn;

    }
}
