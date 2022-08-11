package com.agencyplatformclonecoding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdCampaign is a Querydsl query type for AdCampaign
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdCampaign extends EntityPathBase<AdCampaign> {

    private static final long serialVersionUID = -1831326383L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdCampaign adCampaign = new QAdCampaign("adCampaign");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final NumberPath<Long> budget = createNumber("budget", Long.class);

    public final QClientUser clientUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final SetPath<Creative, QCreative> creatives = this.<Creative, QCreative>createSet("creatives", Creative.class, QCreative.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath name = createString("name");

    public QAdCampaign(String variable) {
        this(AdCampaign.class, forVariable(variable), INITS);
    }

    public QAdCampaign(Path<? extends AdCampaign> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdCampaign(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdCampaign(PathMetadata metadata, PathInits inits) {
        this(AdCampaign.class, metadata, inits);
    }

    public QAdCampaign(Class<? extends AdCampaign> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clientUser = inits.isInitialized("clientUser") ? new QClientUser(forProperty("clientUser"), inits.get("clientUser")) : null;
    }

}

