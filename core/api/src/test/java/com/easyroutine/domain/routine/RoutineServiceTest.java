package com.easyroutine.domain.routine;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.domain.member.MemberStatus;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine.dto.RoutineListDto;
import com.easyroutine.domain.routine_exercise.RoutineExercise;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseListDto;
import com.easyroutine.domain.routine_exercise_sets.RoutineExerciseSets;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetListDto;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.easyroutine.repository.exercises.ExercisesRepository;
import com.easyroutine.repository.member.MemberRepository;
import com.easyroutine.repository.routine.RoutineRepository;
import com.easyroutine.repository.routine_exercise.RoutineExerciseRepository;
import com.easyroutine.repository.routine_exercise_sets.RoutineExerciseSetsRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
public class RoutineServiceTest extends IntegrationTestSupport {

	@Autowired
	private EntityManager em;

	@Autowired
	private RoutineService routineService;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RoutineExerciseRepository routineExerciseRepository;

	@Autowired
	private ExercisesRepository exercisesRepository;

	@Autowired
	private RoutineExerciseSetsRepository routineExerciseSetsRepository;

	@DisplayName("루틴을 생성한다.")
	// @Test
	void createRoutine() {
		// given
		RoutineDto routineDto = getMockRoutineDto();
		Member member = memberRepository.save(getMember("google", "1234", "tester"));
		Exercise exercise = exercisesRepository.save(getExercise(member));

		routineDto.setMemberIdFromToken(member.getId());
		routineDto.getRoutineExerciseDtoList().forEach(dto -> dto.setExerciseId(exercise.getId()));

		// when
		routineService.createRoutine(routineDto);

		assertThat(routineRepository.findAll()).hasSize(1)
			.extracting("name", "color")
			.containsExactly(tuple("test-routine", "test-color"));
	}

	@DisplayName("회원의 루틴 목록을 조회한다.")
	// @Test
	void findAllRoutine() {
		// given
		Member member = memberRepository.save(getMember("google", "1234", "tester"));
		Exercise exercise = exercisesRepository.save(getExercise(member));
		// 루틴 저장
		Routine routine = routineRepository
			.save(Routine.builder().name("test-routine").color("test-color").order(1).member(member).build());

		// 양방향 관계 세팅 후 저장
		RoutineExercise routineExercise = RoutineExercise.builder().order(1).exercise(exercise).build();
		routine.addRoutineExercise(routineExercise);
		routineExercise = routineExerciseRepository.save(routineExercise);

		RoutineExerciseSets sets = RoutineExerciseSets.builder()
			.routineExercise(routineExercise)
			.weight(50.0)
			.rep(10)
			.refreshSec(60)
			.order(1)
			.build();
		routineExerciseSetsRepository.save(sets);

		// 강제 flush & clear to reload relationships
		em.flush();
		em.clear();

		// when
		List<RoutineListDto> result = routineService.findAllRoutine(member);

		// then
		assertThat(result).hasSize(1).extracting("name", "color").containsExactly(tuple("test-routine", "test-color"));

		RoutineListDto routineDto = result.get(0);
		assertThat(routineDto.getRoutineExercises()).hasSize(1);
		RoutineExerciseListDto routineExerciseDto = routineDto.getRoutineExercises().get(0);
		assertThat(routineExerciseDto.getOrder()).isEqualTo(1);
		assertThat(routineExerciseDto.getSets()).hasSize(1);
		RoutineExerciseSetListDto setsDto = routineExerciseDto.getSets().get(0);
		assertThat(setsDto.getWeight()).isEqualTo(50.0);
		assertThat(setsDto.getRep()).isEqualTo(10);
		assertThat(setsDto.getRestSec()).isEqualTo("1:30");
	}

	@DisplayName("루틴을 삭제한다.")
	// @Test
	void deleteRoutine() {
		// given
		Member member = memberRepository.save(getMember("google", "1234", "tester"));
		Exercise exercise = exercisesRepository.save(getExercise(member));

		Routine routine = Routine.builder().name("delete-test").color("blue").member(member).build();

		routine = routineRepository.save(routine);

		RoutineExercise routineExercise = RoutineExercise.builder().order(1).exercise(exercise).build();
		routine.addRoutineExercise(routineExercise);
		routineExercise = routineExerciseRepository.save(routineExercise);

		RoutineExerciseSets sets = RoutineExerciseSets.builder()
			.routineExercise(routineExercise)
			.weight(30.0)
			.rep(5)
			.refreshSec(60)
			.order(1)
			.build();
		routineExerciseSetsRepository.save(sets);

		em.flush();
		em.clear();

		// when

		RoutineDto routineDto = routineService.deleteRoutine(routine.getId(), member);

		// then: DTO 반환 값 검증
		assertThat(routineDto.getId()).isEqualTo(routine.getId());
		assertThat(routineDto.getName()).isEqualTo("delete-test");

		// and: 실제 엔티티의 deletedAt 필드가 셋팅되었는지 검증
		Routine deletedEntity = routineRepository.findByIdAndMember(routine.getId(), member).orElseThrow();
		assertThat(deletedEntity.getDeletedAt()).isNotNull();

		// 연관된 RoutineExercise에도 deletedAt이 찍혔는지 확인
		assertThat(deletedEntity.getRoutineExercises()).hasSize(1);
		RoutineExercise fetchedExercise = deletedEntity.getRoutineExercises().get(0);
		assertThat(fetchedExercise.getDeletedAt()).isNotNull();

		// 세트에도 deletedAt이 찍혔는지 확인
		assertThat(fetchedExercise.getSets()).hasSize(1);
		RoutineExerciseSets fetchedSet = fetchedExercise.getSets().get(0);
		assertThat(fetchedSet.getDeletedAt()).isNotNull();
	}

	private static RoutineDto getMockRoutineDto() {
		return RoutineDto.builder()
			.name("test-routine")
			.color("test-color")
			.order(1)
			.routineExerciseDtoList(List.of(getRoutineExerciseDto()))
			.build();
	}

	private static RoutineExerciseDto getRoutineExerciseDto() {
		return RoutineExerciseDto.builder()
			.order(1)
			.routineExerciseSetsDtoList(List.of(getRoutineExerciseSetsDto()))
			.build();
	}

	private static RoutineExerciseSetsDto getRoutineExerciseSetsDto() {
		return RoutineExerciseSetsDto.builder().weight(50.0).rep(10).refreshSec(60).build();
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

	private static Exercise getExercise(Member member) {
		return Exercise.of(member);
	}

}
