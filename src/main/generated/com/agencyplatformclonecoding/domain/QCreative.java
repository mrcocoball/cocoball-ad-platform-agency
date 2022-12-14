package com.agencyplatformclonecoding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCreative is a Querydsl query type for Creative
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCreative extends EntityPathBase<Creative> {

    private static final long serialVersionUID = 851093037L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCreative creative = new QCreative("creative");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final BooleanPath activated = createBoolean("activated");

    public final NumberPath<Long> bidingPrice = createNumber("bidingPrice", Long.class);

    public final QCampaign campaign;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final SetPath<Performance, QPerformance> performances = this.<Performance, QPerformance>createSet("performances", Performance.class, QPerformance.class, PathInits.DIRECT2);

    public final StringPath url = createString("url");

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public QCreative(String variable) {
        this(Creative.class, forVariable(variable), INITS);
    }

    public QCreative(Path<? extends Creative> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCreative(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCreative(PathMetadata metadata, PathInits inits) {
        this(Creative.class, metadata, inits);
    }

    public QCreative(Class<? extends Creative> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.campaign = inits.isInitialized("campaign") ? new QCampaign(forProperty("campaign"), inits.get("campaign")) : null;
    }

}

