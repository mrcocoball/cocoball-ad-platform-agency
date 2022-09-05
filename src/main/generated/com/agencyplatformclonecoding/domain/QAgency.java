package com.agencyplatformclonecoding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgency is a Querydsl query type for Agency
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgency extends EntityPathBase<Agency> {

    private static final long serialVersionUID = -1965958653L;

    public static final QAgency agency = new QAgency("agency");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final StringPath agencyId = createString("agencyId");

    public final StringPath agencyName = createString("agencyName");

    public final SetPath<AgentGroup, QAgentGroup> agentGroups = this.<AgentGroup, QAgentGroup>createSet("agentGroups", AgentGroup.class, QAgentGroup.class, PathInits.DIRECT2);

    public final SetPath<Agent, QAgent> agents = this.<Agent, QAgent>createSet("agents", Agent.class, QAgent.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath password = createString("password");

    public QAgency(String variable) {
        super(Agency.class, forVariable(variable));
    }

    public QAgency(Path<? extends Agency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgency(PathMetadata metadata) {
        super(Agency.class, metadata);
    }

}

