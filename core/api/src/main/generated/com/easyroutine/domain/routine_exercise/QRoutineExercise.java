package com.easyroutine.domain.routine_exercise;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutineExercise is a Querydsl query type for RoutineExercise
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutineExercise extends EntityPathBase<RoutineExercise> {

    private static final long serialVersionUID = -449756622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutineExercise routineExercise = new QRoutineExercise("routineExercise");

    public final com.easyroutine.domain.QBaseEntity _super = new com.easyroutine.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final com.easyroutine.domain.exercises.QExercise exercise;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final com.easyroutine.domain.routine.QRoutine routine;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRoutineExercise(String variable) {
        this(RoutineExercise.class, forVariable(variable), INITS);
    }

    public QRoutineExercise(Path<? extends RoutineExercise> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutineExercise(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutineExercise(PathMetadata metadata, PathInits inits) {
        this(RoutineExercise.class, metadata, inits);
    }

    public QRoutineExercise(Class<? extends RoutineExercise> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exercise = inits.isInitialized("exercise") ? new com.easyroutine.domain.exercises.QExercise(forProperty("exercise"), inits.get("exercise")) : null;
        this.routine = inits.isInitialized("routine") ? new com.easyroutine.domain.routine.QRoutine(forProperty("routine"), inits.get("routine")) : null;
    }

}

