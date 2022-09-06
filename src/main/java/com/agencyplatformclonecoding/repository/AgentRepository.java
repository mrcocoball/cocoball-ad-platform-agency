package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.QAgent;
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
public interface AgentRepository extends
        JpaRepository<Agent, String>,
        QuerydslPredicateExecutor<Agent>,
        QuerydslBinderCustomizer<QAgent> {

    Page<Agent> findByUserIdContaining(String userId, Pageable pageable);
    Page<Agent> findByNicknameContaining(String nickname, Pageable pageable);

    List<Agent> findByAgentGroup_Id(Long agentGroupId);

    void deleteByUserIdAndAgency_AgencyId(String agentId, String agencyId);

    @Override
    default void customize(QuerydslBindings bindings, QAgent root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.userId, root.nickname, root.createdAt, root.createdBy);
        bindings.bind(root.nickname).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
