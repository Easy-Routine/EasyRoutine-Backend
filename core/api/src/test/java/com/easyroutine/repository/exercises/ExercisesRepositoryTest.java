package com.easyroutine.repository.exercises;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.ExerciseType;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.domain.member.MemberStatus;
import com.easyroutine.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.easyroutine.domain.exercises.ExerciseCategory.*;
import static com.easyroutine.domain.exercises.ExerciseType.*;
import static org.assertj.core.api.Assertions.assertThat;

class ExercisesRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        exercisesRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("운동 종목을 조회한다.")
    @ParameterizedTest
    @ValueSource(strings = {"CHEST", "BACK", "SHOULDER", "LEG", "ARM", "ETC"})
    void getExercises(String category) {
    
        // given
        Member member = getMember("google", "1234", "tester");
        Exercise exerciseA = getExerciseEntity("exerciseA", List.of(WEIGHT), CHEST, member);
        Exercise exerciseB = getExerciseEntity("exerciseB", List.of(TIME), BACK, member);
        Exercise exerciseC = getExerciseEntity("exerciseC", List.of(COUNT), SHOULDER, member);
        Exercise exerciseD = getExerciseEntity("exerciseD", List.of(WEIGHT, TIME), LEG, member);
        Exercise exerciseE = getExerciseEntity("exerciseE", List.of(WEIGHT, COUNT), ARM, member);
        Exercise exerciseF = getExerciseEntity("exerciseF", List.of(TIME, COUNT), ETC, member);
        Exercise exerciseG = getExerciseEntity("exerciseG", List.of(WEIGHT, TIME, COUNT), ETC, member);

        memberRepository.save(member);
        exercisesRepository.saveAll(List.of(
                exerciseA,
                exerciseB,
                exerciseC,
                exerciseD,
                exerciseE,
                exerciseF,
                exerciseG
        ));
    
        // when
        List<Exercise> exercises = exercisesRepository.findAllByCategory(category, PageRequest.of(0, 10));

        // then
        assertThat(exercises)
                .hasSizeGreaterThan(0)
                .allMatch(exercise -> exercise.getCategory() == ExerciseCategory.convertToEnum(category));
    }

    @DisplayName("'ALL'은 모든 운동 종목을 조회한다.")
    @Test
    void getAllExercises(){

        // given
        Member member = getMember("google", "1234", "tester");
        Exercise exerciseA = getExerciseEntity("exerciseA", List.of(WEIGHT), CHEST, member);
        Exercise exerciseB = getExerciseEntity("exerciseB", List.of(TIME), BACK, member);
        Exercise exerciseC = getExerciseEntity("exerciseC", List.of(COUNT), SHOULDER, member);
        Exercise exerciseD = getExerciseEntity("exerciseD", List.of(WEIGHT, TIME), LEG, member);
        Exercise exerciseE = getExerciseEntity("exerciseE", List.of(WEIGHT, COUNT), ARM, member);
        Exercise exerciseF = getExerciseEntity("exerciseF", List.of(TIME, COUNT), ETC, member);
        Exercise exerciseG = getExerciseEntity("exerciseG", List.of(WEIGHT, TIME, COUNT), ETC, member);

        memberRepository.save(member);
        exercisesRepository.saveAll(List.of(
                exerciseA,
                exerciseB,
                exerciseC,
                exerciseD,
                exerciseE,
                exerciseF,
                exerciseG
        ));

        // when
        String allCategoryKeyword = "ALL";
        List<Exercise> allByCategory = exercisesRepository.findAllByCategory(allCategoryKeyword, PageRequest.of(0, 10));

        // then
        assertThat(allByCategory)
                .hasSize(7)
                .extracting("category")
                .containsExactlyInAnyOrder(CHEST, BACK, SHOULDER, LEG, ARM, ETC, ETC);
    }

    private static Exercise getExerciseEntity(String name, List<ExerciseType> types, ExerciseCategory category, Member member) {
        return Exercise.builder()
                .name(name)
                .types(types)
                .category(category)
                .member(member)
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
}