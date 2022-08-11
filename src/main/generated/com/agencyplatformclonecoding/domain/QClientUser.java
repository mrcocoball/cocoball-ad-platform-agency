package com.agencyplatformclonecoding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClientUser is a Querydsl query type for ClientUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClientUser extends EntityPathBase<ClientUser> {

    private static final long serialVersionUID = 1585186100L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClientUser clientUser = new QClientUser("clientUser");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final SetPath<AdCampaign, QAdCampaign> adCampaigns = this.<AdCampaign, QAdCampaign>createSet("adCampaigns", AdCampaign.class, QAdCampaign.class, PathInits.DIRECT2);

    public final QAgency agency;

    public final QAgent agent;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath email = createString("email");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath nickname = createString("nickname");

    public final StringPath userId = createString("userId");

    public final StringPath userPassword = createString("userPassword");

    public QClientUser(String variable) {
        this(ClientUser.class, forVariable(variable), INITS);
    }

    public QClientUser(Path<? extends ClientUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClientUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClientUser(PathMetadata metadata, PathInits inits) {
        this(ClientUser.class, metadata, inits);
    }

    public QClientUser(Class<? extends ClientUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agency = inits.isInitialized("agency") ? new QAgency(forProperty("agency")) : null;
        this.agent = inits.isInitialized("agent") ? new QAgent(forProperty("agent"), inits.get("agent")) : null;
    }

}

