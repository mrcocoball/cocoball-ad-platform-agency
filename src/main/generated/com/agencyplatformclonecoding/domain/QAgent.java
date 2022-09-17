package com.agencyplatformclonecoding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgent is a Querydsl query type for Agent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgent extends EntityPathBase<Agent> {

    private static final long serialVersionUID = -1171796665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgent agent = new QAgent("agent");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final QAgency agency;

    public final QAgentGroup agentGroup;

    public final SetPath<ClientUser, QClientUser> clientUsers = this.<ClientUser, QClientUser>createSet("clientUsers", ClientUser.class, QClientUser.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath email = createString("email");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath nickname = createString("nickname");

    public final StringPath userId = createString("userId");

    public final StringPath userPassword = createString("userPassword");

    public QAgent(String variable) {
        this(Agent.class, forVariable(variable), INITS);
    }

    public QAgent(Path<? extends Agent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgent(PathMetadata metadata, PathInits inits) {
        this(Agent.class, metadata, inits);
    }

    public QAgent(Class<? extends Agent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agency = inits.isInitialized("agency") ? new QAgency(forProperty("agency")) : null;
        this.agentGroup = inits.isInitialized("agentGroup") ? new QAgentGroup(forProperty("agentGroup"), inits.get("agentGroup")) : null;
    }

}

