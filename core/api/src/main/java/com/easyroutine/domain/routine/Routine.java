package com.easyroutine.domain.routine;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine_exercise.RoutineExercise;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "routines")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private String name;

	@Column
	private String color;

	@Column(name = "order_index", nullable = false)
	private int order;

	@OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RoutineExercise> routineExercises = new ArrayList<>();

	public List<RoutineExercise> getRoutineExercises() {
		if (routineExercises == null) {
			routineExercises = new ArrayList<>();
		}
		return routineExercises;
	}

	public static Routine of(Long id) {
		return Routine.builder().id(id).build();
	}

	/**
	 * for test code helper method
	 */

	public void addRoutineExercise(RoutineExercise routineExercise) {
		this.getRoutineExercises().add(routineExercise);
		routineExercise.setroutine(this);
	}

}
