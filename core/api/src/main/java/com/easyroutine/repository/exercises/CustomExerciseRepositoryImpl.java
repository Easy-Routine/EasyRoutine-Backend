package com.easyroutine.repository.exercises;

import com.easyroutine.domain.exercises.Exercise;
import com.easyroutine.domain.exercises.ExerciseCategory;
import com.easyroutine.domain.exercises.QExercise;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomExerciseRepositoryImpl implements CustomExerciseRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Exercise> findAllByCategory(String category, Pageable pageable) {

        QExercise exercise = QExercise.exercise;

        return jpaQueryFactory
                .selectFrom(exercise)
                .where(categoryEq(category, exercise))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression categoryEq(String category, QExercise exercise) {

        if("ALL".equals(category)) {
            return null;
        }
        ExerciseCategory exerciseCategory = ExerciseCategory.convertToEnum(category);
        return exercise.category.eq(exerciseCategory);
    }
}
