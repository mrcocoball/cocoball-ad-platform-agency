package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.AgentGroup;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.QAgentGroup;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AgentGroupRepository extends
        JpaRepository<AgentGroup, Long>,
        QuerydslPredicateExecutor<AgentGroup>,
        QuerydslBinderCustomizer<QAgentGroup> {

    Page<AgentGroup> findByIdContaining(String id, Pageable pageable);

    Page<AgentGroup> findByNameContaining(String nickname, Pageable pageable);

    void deleteByIdAndAgency_AgencyId(Long agentGroupId, String agencyId);

    @Override
    default void customize(QuerydslBindings bindings, QAgentGroup root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.name, root.createdAt, root.createdBy);
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}

