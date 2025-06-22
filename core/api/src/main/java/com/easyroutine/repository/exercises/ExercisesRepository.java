package com.easyroutine.repository.exercises;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Long>, CustomExerciseRepository {

	Optional<Exercise> findByIdAndMemberAndDeletedAtIsNull(Long id, Member member);

	Optional<Exercise> findByName(String name);

}
