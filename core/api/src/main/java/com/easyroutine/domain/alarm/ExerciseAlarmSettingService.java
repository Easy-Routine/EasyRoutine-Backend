package com.easyroutine.domain.alarm;

import com.easyroutine.domain.alarm.dto.ExerciseAlarmSettingDto;
import com.easyroutine.domain.member.Member;
import com.easyroutine.repository.exercise_alarm_setting.ExerciseAlarmSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseAlarmSettingService {

	private final ExerciseAlarmSettingRepository exerciseAlarmSettingRepository;

	@Transactional(rollbackFor = Exception.class)
	public Long saveExerciseAlarmSetting(
			ExerciseAlarmSettingDto.ReqSaveExerciseAlarmSetting reqSaveExerciseAlarmSetting, Member member) {
		return exerciseAlarmSettingRepository.save(ExerciseAlarmSetting.of(reqSaveExerciseAlarmSetting, member))
			.getId();
	}

	public ExerciseAlarmSettingDto.ResFindExerciseAlarmSetting findExerciseAlarmSettingById(Member member) {
		return exerciseAlarmSettingRepository.findByMember(member).from();
	}

}
