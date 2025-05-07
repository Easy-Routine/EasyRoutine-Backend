package com.easyroutine.domain.exercises;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.domain.member.MemberStatus;
import com.easyroutine.repository.exercises.ExercisesRepository;
import com.easyroutine.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class ExerciseServiceTest extends IntegrationTestSupport {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        exercisesRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("운동을 생성한다.")
    @Test
    void createExercise(){

        // given
        Member member = getMember("google", "1234", "tester");
        memberRepository.save(member);

        ExerciseDto exerciseDto = getExerciseDto();

        // when
        String result = exerciseService.createExercise(exerciseDto, member.getId());
        List<ExerciseDto> exercises = exerciseService.getExercises("CHEST", 0, 10);

        // then
        assertThat(result).isEqualTo("Success");
        assertThat(exercises)
                .hasSize(1)
                .extracting("name", "category")
                .containsExactlyInAnyOrder(
                        tuple("exerciseName", ExerciseCategory.CHEST)
                );
    }

    @DisplayName("운동을 조회한다.")
    @Test
    void getExercises() {

        // given
        Member member = getMember("google", "1234", "tester");
        memberRepository.save(member);

        ExerciseDto exerciseDto = getExerciseDto();
        exerciseService.createExercise(exerciseDto, member.getId());

        // when
        List<ExerciseDto> exercises = exerciseService.getExercises("ALL", 0, 10);

        // then
        assertThat(exercises)
                .hasSize(1)
                .satisfiesExactly(exercise -> {
                    assertThat(exercise.getName()).isEqualTo("exerciseName");
                    assertThat(exercise.getCategory()).isEqualTo(ExerciseCategory.CHEST);
                    assertThat(exercise.getTypes()).containsExactlyInAnyOrder(ExerciseType.WEIGHT);
                    assertThat(exercise.getImage()).isEqualTo("http://example.com/image.jpg");
                    assertThat(exercise.getIsEditable()).isEqualTo(1);
                    assertThat(exercise.getShareLevel()).isEqualTo(1);
                });
    }

    @DisplayName("운동을 수정한다.")
    @Test
    void updateExercise(){

        // given
        Member member = getMember("google", "1234", "tester");
        memberRepository.save(member);

        ExerciseDto exerciseDto = getExerciseDto();
        exerciseService.createExercise(exerciseDto, member.getId());

        // when
        ExerciseDto updateExerciseDto = getExerciseDto(1L);
        exerciseService.updateExercise(updateExerciseDto, member.getId());

        // then
        List<ExerciseDto> exercises = exerciseService.getExercises("ALL", 0, 10);
        assertThat(exercises)
                .hasSize(1)
                .satisfiesExactly(exercise -> {
                    assertThat(exercise.getName()).isEqualTo("update-exerciseName");
                    assertThat(exercise.getCategory()).isEqualTo(ExerciseCategory.ARM);
                    assertThat(exercise.getTypes()).containsExactlyInAnyOrder(ExerciseType.WEIGHT, ExerciseType.COUNT);
                    assertThat(exercise.getImage()).isEqualTo("http://update-example.com/image.jpg");
                    assertThat(exercise.getIsEditable()).isEqualTo(0);
                    assertThat(exercise.getShareLevel()).isEqualTo(0);
                });
    }

    @DisplayName("운동을 삭제한다.")
    @Test
    void deleteExercise(){

        // given
        Member member = getMember("google", "1234", "tester");
        memberRepository.save(member);

        ExerciseDto exerciseDto = getExerciseDto();
        exerciseService.createExercise(exerciseDto, member.getId());

        // when
        exerciseService.deleteExercise(1L, member.getId());
        List<ExerciseDto> exercises = exerciseService.getExercises("ALL", 0, 10);

        // then
        assertThat(exercises)
                .hasSize(0);
    }

    public static ExerciseDto getExerciseDto(Long id) {
        return ExerciseDto.builder()
                .id(id)
                .name("update-exerciseName")
                .category(ExerciseCategory.ARM)
                .types(List.of(ExerciseType.WEIGHT, ExerciseType.COUNT))
                .image("http://update-example.com/image.jpg")
                .isEditable(0)
                .shareLevel(0)
                .build();
    }

    private static ExerciseDto getExerciseDto() {
        ExerciseDto exerciseDto = ExerciseDto.builder()
                .name("exerciseName")
                .category(ExerciseCategory.CHEST)
                .types(List.of(ExerciseType.WEIGHT))
                .image("http://example.com/image.jpg")
                .isEditable(1)
                .shareLevel(1)
                .build();
        return exerciseDto;
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
}