package com.easyroutine.domain.alarm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

public class ExerciseAlarmSettingDto {

	@Getter
	public static class ReqSaveExerciseAlarmSetting {
		@JsonIgnore
		private long memberId;

		private LocalTime exerciseStartTime;

		private Integer preAlarmMinutes;

		private Boolean alarmYn;

	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class ResFindExerciseAlarmSetting {

		private LocalTime exerciseStartTime;

		private int preAlarmMinutes;

		private boolean alarmYn;

	}

}
