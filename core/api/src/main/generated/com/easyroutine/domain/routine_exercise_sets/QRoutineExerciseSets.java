package com.easyroutine.domain.routine_exercise_sets;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutineExerciseSets is a Querydsl query type for RoutineExerciseSets
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutineExerciseSets extends EntityPathBase<RoutineExerciseSets> {

    private static final long serialVersionUID = -40684981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutineExerciseSets routineExerciseSets = new QRoutineExerciseSets("routineExerciseSets");

    public final com.easyroutine.domain.QBaseEntity _super = new com.easyroutine.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final StringPath refreshTime = createString("refreshTime");

    public final NumberPath<Integer> rep = createNumber("rep", Integer.class);

    public final com.easyroutine.domain.routine_exercise.QRoutineExercise routineExercise;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QRoutineExerciseSets(String variable) {
        this(RoutineExerciseSets.class, forVariable(variable), INITS);
    }

    public QRoutineExerciseSets(Path<? extends RoutineExerciseSets> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutineExerciseSets(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutineExerciseSets(PathMetadata metadata, PathInits inits) {
        this(RoutineExerciseSets.class, metadata, inits);
    }

    public QRoutineExerciseSets(Class<? extends RoutineExerciseSets> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.routineExercise = inits.isInitialized("routineExercise") ? new com.easyroutine.domain.routine_exercise.QRoutineExercise(forProperty("routineExercise"), inits.get("routineExercise")) : null;
    }

}

