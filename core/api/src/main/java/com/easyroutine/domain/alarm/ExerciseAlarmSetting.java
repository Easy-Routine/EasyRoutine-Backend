package com.easyroutine.domain.alarm;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.alarm.dto.ExerciseAlarmSettingDto;
import com.easyroutine.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "exercise_alarm_setting")
@NoArgsConstructor(access = PROTECTED)
public class ExerciseAlarmSetting extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, unique = true)
	private Member member;

	// 운동 시작 알람 시간 (예: 07:30)
	private LocalTime exerciseStartTime;

	// 운동 시작 전에 알림 받을 시간 (단위: 분, 예: 30, 60, 120, 0=설정안함)
	private int preAlarmMinutes;

	// 알림 기능 사용 여부 (on/off)
	private boolean alarmYn;

	@Builder
	public ExerciseAlarmSetting(Long id, Member member, LocalTime exerciseStartTime, Integer preAlarmMinutes,
			Boolean alarmYn) {
		this.member = member;
		this.exerciseStartTime = exerciseStartTime;
		this.preAlarmMinutes = preAlarmMinutes;
		this.alarmYn = alarmYn;
	}

	public void updateAlarm(LocalTime exerciseStartTime, Integer preAlarmMinutes, Boolean alarmYn) {
		this.exerciseStartTime = exerciseStartTime;
		this.preAlarmMinutes = preAlarmMinutes;
		this.alarmYn = alarmYn;
	}

	public static ExerciseAlarmSetting of(ExerciseAlarmSettingDto.ReqSaveExerciseAlarmSetting dto, Member member) {
		return ExerciseAlarmSetting.builder()
			.member(member)
			.exerciseStartTime(dto.getExerciseStartTime())
			.preAlarmMinutes(dto.getPreAlarmMinutes())
			.alarmYn(dto.getAlarmYn())
			.build();
	}

	public ExerciseAlarmSettingDto.ResFindExerciseAlarmSetting from() {
		return ExerciseAlarmSettingDto.ResFindExerciseAlarmSetting.builder()
			.exerciseStartTime(this.exerciseStartTime)
			.preAlarmMinutes(this.preAlarmMinutes)
			.alarmYn(this.alarmYn)
			.build();
	}

}
