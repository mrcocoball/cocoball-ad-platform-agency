package com.agencyplatformclonecoding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgentGroup is a Querydsl query type for AgentGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgentGroup extends EntityPathBase<AgentGroup> {

    private static final long serialVersionUID = -722047624L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgentGroup agentGroup = new QAgentGroup("agentGroup");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final QAgency agency;

    public final SetPath<Agent, QAgent> agents = this.<Agent, QAgent>createSet("agents", Agent.class, QAgent.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath name = createString("name");

    public QAgentGroup(String variable) {
        this(AgentGroup.class, forVariable(variable), INITS);
    }

    public QAgentGroup(Path<? extends AgentGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgentGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgentGroup(PathMetadata metadata, PathInits inits) {
        this(AgentGroup.class, metadata, inits);
    }

    public QAgentGroup(Class<? extends AgentGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agency = inits.isInitialized("agency") ? new QAgency(forProperty("agency")) : null;
    }

}

