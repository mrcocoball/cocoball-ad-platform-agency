package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.QAgent;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AgentRepository extends
        JpaRepository<Agent, String>,
        QuerydslPredicateExecutor<Agent>,
        QuerydslBinderCustomizer<QAgent> {

    List<Agent> findByAgentGroup_IdAndDeletedFalse(Long agentGroupId);

   	Page<Agent> findByDeletedFalse(Pageable pageable);

   	Optional<Agent> findByUserIdAndDeletedFalse(String agentId);

   	Page<Agent> findByUserIdContainingAndDeletedFalse(String userId, Pageable pageable);

    Page<Agent> findByNicknameContainingAndDeletedFalse(String nickname, Pageable pageable);

    long countByDeletedFalse();

    @Modifying
   	@Transactional
   	@Query("UPDATE Agent a SET a.deleted = true where a.userId = :agentId and a.agency.agencyId = :agencyId")
    void setAgentDeletedTrue(@Param("agentId") String agentId,
   							 @Param("agencyId") String agencyId);

    @Override
    default void customize(QuerydslBindings bindings, QAgent root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.userId, root.nickname, root.createdAt, root.createdBy);
        bindings.bind(root.nickname).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
