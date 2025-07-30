package com.easyroutine.repository.exercise_alarm_setting;

import com.easyroutine.domain.alarm.ExerciseAlarmSetting;
import com.easyroutine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseAlarmSettingRepository extends JpaRepository<ExerciseAlarmSetting, Long> {

	ExerciseAlarmSetting findByMember(Member member);

}
