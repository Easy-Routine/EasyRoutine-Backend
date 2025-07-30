package com.easyroutine.repository.exercises;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.easyroutine.domain.exercises.QExercise.exercise;

@RequiredArgsConstructor
public class CustomExerciseRepositoryImpl implements CustomExerciseRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Exercise> findAllByCategoryAndDeletedAtIsNull(String category, Pageable pageable, String keyword,
			String memberId) {

		JPAQuery<Exercise> query = jpaQueryFactory.selectFrom(exercise)
			.where(categoryEq(category), nameEq(keyword), shareLevelEq(memberId), exercise.deletedAt.isNull());

		if (pageable.isPaged()) {
			query.offset(pageable.getOffset()).limit(pageable.getPageSize());
		}

		return query.fetch();
	}

	private Predicate shareLevelEq(String memberId) {
		return exercise.shareLevel.eq(0).or((exercise.member.id.eq(memberId)).and(exercise.shareLevel.eq(1)));
	}

	private Predicate categoryEq(String category) {
		return StringUtils.isEmpty(category) || "ALL".equals(category) ? null
				: exercise.category.eq(ExerciseCategory.valueOf(category.toUpperCase()));
	}

	private Predicate nameEq(String keyword) {
		return StringUtils.isEmpty(keyword) ? null : exercise.name.containsIgnoreCase(keyword);
	}

}
