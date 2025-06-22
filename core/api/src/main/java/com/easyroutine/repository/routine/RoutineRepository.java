package com.easyroutine.repository.routine;

import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.Routine;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {

	@EntityGraph(attributePaths = { "routineExercises" })
	@Query("SELECT r FROM Routine r WHERE r.member = :member")
	List<Routine> findWithExercisesByMember(@Param("member") Member member);

	List<Routine> findByMember(Member member);

	Optional<Routine> findByIdAndMember(Long id, Member member);

}
