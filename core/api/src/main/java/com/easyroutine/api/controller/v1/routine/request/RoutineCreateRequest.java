package com.easyroutine.api.controller.v1.routine.request;

import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class RoutineCreateRequest {
	private String name;
	private String color;
	private int order;
	private List<ReqRoutineExercise> routineExercises;

	@JsonIgnore
	@Valid
	public RoutineDto routineDto;

	public void ofRoutineCreateRequest() {
		List<RoutineExerciseDto> routineExerciseDtoList = routineExercises.stream().map(exercise -> {
			List<RoutineExerciseSetsDto> sets = exercise.getSets().stream().map(set ->
					RoutineExerciseSetsDto.builder()
							.order(set.getOrder())
							.weight(Double.parseDouble(set.getWeight())) // weight는 String으로 받지만 Double로 변환
							.rep(Integer.parseInt(set.getRep()))         // rep도 String → int
							.restSec(set.getRestSec())   // restSec이 숫자라면 문자열로 변환
							.build()
			).toList();

			return RoutineExerciseDto.builder()
					.exerciseId((long) exercise.getExercise().getId())
					.order(exercise.getOrder())
					.routineExerciseSetsDtoList(sets)
					.build();
		}).toList();

		this.routineDto = RoutineDto.builder()
				.name(this.name)
				.color(this.color)
				.order(this.order)
				.routineExerciseDtoList(routineExerciseDtoList)
				.build();
	}


	@Data
	public static class ReqRoutineExercise {
		private int order;
		private ReqExercise exercise;
		private List<ReqSets> sets;
	}

	@Data
	public static class ReqExercise {
		private int id;
	}

	@Data
	public static class ReqSets {
		private int order;
		private String weight;
		private String rep;
		private String exerciseSec;
		private String restSec;
	}



}