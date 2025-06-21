package com.easyroutine.domain.exercises;

import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.domain.member.Member;
import com.easyroutine.global.exception.DataException;
import com.easyroutine.global.response.ResultType;
import com.easyroutine.repository.exercises.ExercisesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ExerciseService {

	private final ExercisesRepository exerciseRepository;

	public ExerciseService(ExercisesRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
	}

	public List<Exercise> getExercises(String category, int page, int size, String keyword, String memberId) {
		Pageable pageable = (page == 0 || size == 0) ? Pageable.unpaged() : PageRequest.of(page, size);
		return exerciseRepository.findAllByCategoryAndDeletedAtIsNull(category, pageable, keyword, memberId);
	}

	@Transactional(rollbackFor = Exception.class)
	public String createExercise(ExerciseDto exerciseDto, String memberId) {
		Member member = Member.of(memberId);
		Exercise exercise = Exercise.of(exerciseDto, member);

		exerciseRepository.save(exercise);

		return "Success";
	}

	@Transactional(rollbackFor = Exception.class)
	public String updateExercise(ExerciseDto exerciseDto, String memberId) {
		Member member = Member.of(memberId);
		Optional<Exercise> exerciseOptional = exerciseRepository
			.findByIdAndMemberAndDeletedAtIsNull(exerciseDto.getId(), member);

		exerciseOptional.ifPresentOrElse(exercise -> {
			exercise.updateExercise(exerciseDto);
		}, () -> {
			throw new DataException(ResultType.DATA_NOT_FOUND, "운동을 찾을 수 없습니다.");
		});

		return "Success";
	}

	@Transactional(rollbackFor = Exception.class)
	public String deleteExercise(Long id, String memberId) {
		Member member = Member.of(memberId);
		Optional<Exercise> exerciseOptional = exerciseRepository.findByIdAndMemberAndDeletedAtIsNull(id, member);

		exerciseOptional.ifPresentOrElse(Exercise::deleteExercise, () -> {
			throw new DataException(ResultType.DATA_NOT_FOUND, "운동을 찾을 수 없습니다.");
		});

		return "Success";
	}

}
