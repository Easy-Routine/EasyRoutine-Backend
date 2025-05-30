package com.easyroutine.domain.routine;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.domain.member.MemberStatus;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.easyroutine.repository.exercises.ExercisesRepository;
import com.easyroutine.repository.member.MemberRepository;
import com.easyroutine.repository.routine.RoutineRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
public class RoutineServiceTest extends IntegrationTestSupport {

    @Autowired
    private RoutineService routineService;

    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExercisesRepository exercisesRepository;

    @DisplayName("루틴을 생성한다.")
    @Test
    void createRoutine(){
        //given
        RoutineDto routineDto = getMockRoutineDto();
        Member member = memberRepository.save(getMember("google", "1234", "tester"));
        Exercise exercise = exercisesRepository.save(getExercise(member));

        routineDto.setMemberIdFromToken(member.getId());
        routineDto.getRoutineExerciseDtoList().forEach(dto -> dto.setExerciseId(exercise.getId()));



        //when
        routineService.createRoutine(routineDto);

        assertThat(routineRepository.findAll())
                .hasSize(1)
                .extracting("name","color")
                .containsExactly(tuple("test-routine","test-color"));
    }

    @DisplayName("회원의 루틴 목록을 조회한다.")
    @Test
    void findAllRoutine() {
        // given
        Member member = memberRepository.save(getMember("google", "1234", "tester"));
        Exercise exercise = exercisesRepository.save(getExercise(member));

        RoutineDto routineDto = getMockRoutineDto();
        routineDto.setMemberIdFromToken(member.getId());
        routineDto.getRoutineExerciseDtoList ().forEach(dto -> dto.setExerciseId(exercise.getId()));

        routineService.createRoutine(routineDto);

        // when
        List<RoutineDto> result = routineService.findAllRoutine(member);

        // then
        assertThat(result)
                .hasSize(1)
                .extracting("name", "color")
                .containsExactly(tuple("test-routine", "test-color"));
    }



    private static RoutineDto getMockRoutineDto(){
        return RoutineDto.builder()
                .name("test-routine")
                .color("test-color")
                .routineExerciseDtoList(List.of(getRoutineExerciseDto()))
                .build();
    }

    private static RoutineExerciseDto getRoutineExerciseDto(){
        return RoutineExerciseDto.builder()
                .order(1)
                .setsDtoList(List.of(getRoutineExerciseSetsDto()))
                .build();
    }

    private static RoutineExerciseSetsDto getRoutineExerciseSetsDto(){
        return RoutineExerciseSetsDto.builder()
                .weight(50.0)
                .rep(10)
                .restSec("1:30")
                .build();
    }

    private static Member getMember(String provider, String providerId, String nickname) {
        return Member.builder()
                .provider(provider)
                .providerId(providerId)
                .nickname(nickname)
                .email("" + nickname + "@example.com")
                .masking_email("" + nickname + "@example.com")
                .role(MemberRole.MEMBER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    private static Exercise getExercise(Member member){
        return Exercise.of(member);
    }

}
