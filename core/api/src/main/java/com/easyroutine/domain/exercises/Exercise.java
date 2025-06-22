package com.easyroutine.domain.exercises;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.exercises.dto.ExerciseDto;
import com.easyroutine.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "exercises")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "register_id", nullable = false)
	private Member member;

	@Column
	private String name;

	@Column
	private String image;

	@Column(name = "origin_image")
	private String originImage;

	@Column(length = 50)
	@Enumerated(EnumType.STRING)
	private ExerciseCategory category;

	@ElementCollection
	@CollectionTable(name = "exercise_types", joinColumns = @JoinColumn(name = "exercise_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 50)
	private List<ExerciseType> types = new ArrayList<>();

	@Column(name = "is_editable")
	private int isEditable;

	@Column(name = "share_level")
	private int shareLevel;

	@Builder
	private Exercise(Long id, Member member, String name, String image, String originImage, ExerciseCategory category,
			List<ExerciseType> types, int isEditable, int shareLevel) {
		this.id = id;
		this.member = member;
		this.name = name;
		this.image = image;
		this.originImage = originImage;
		this.category = category;
		this.types = types;
		this.isEditable = isEditable;
		this.shareLevel = shareLevel;
	}

	public static Exercise of(Long id) {
		return Exercise.builder().id(id).build();
	}

	public static Exercise of(Member member) {
		return Exercise.builder().member(member).build();
	}

	public static Exercise of(ExerciseDto exerciseDto, Member member) {
		return Exercise.builder()
			.member(member)
			.name(exerciseDto.getName())
			.image(exerciseDto.getImage())
			.originImage(exerciseDto.getOriginImage())
			.category(exerciseDto.getCategory())
			.types(exerciseDto.getTypes())
			.isEditable(exerciseDto.getIsEditable())
			.shareLevel(exerciseDto.getShareLevel())
			.build();
	}

	public void updateExercise(ExerciseDto exerciseDto) {
		this.name = exerciseDto.getName();
		this.image = exerciseDto.getImage();
		this.originImage = exerciseDto.getOriginImage();
		this.category = exerciseDto.getCategory();
		this.types = exerciseDto.getTypes();
		this.isEditable = exerciseDto.getIsEditable();
		this.shareLevel = exerciseDto.getShareLevel();
		this.setUpdatedAt();
	}

	public void deleteExercise() {
		this.setDeletedAt();
	}

	public boolean isCreatedBy(String memberId) {
		return this.member.getId().equals(memberId);
	}

	public boolean isNotCreatedBy(String memberId) {
		return !isCreatedBy(memberId);
	}

}
