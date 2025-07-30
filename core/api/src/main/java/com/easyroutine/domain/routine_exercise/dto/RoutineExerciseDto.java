package com.easyroutine.domain.routine_exercise.dto;

import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseDto {

	@JsonIgnore
	private Long id;

	@JsonIgnore
	private Long routineId;

	@NotNull(message = "운동 아이디가 없습니다.")
	private Long exerciseId;

	@Min(value = 1, message = "운동 순서는 1 이상이어야 합니다.")
	@NotNull(message = "운동 순서는 필 수 입니다.")
	private int order;

	private ExerciseCategory category;

	private String image;

	private String name;

	@NotEmpty(message = "세트 리스트는 비어 있을 수 없습니다.")
	@Valid
	private List<@Valid RoutineExerciseSetsDto> routineExerciseSetsDtoList;

	public void setRoutineId(Long routineId) {
		this.routineId = routineId;
	}

	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

	// order
	// exercise
	// sets
	//

}
