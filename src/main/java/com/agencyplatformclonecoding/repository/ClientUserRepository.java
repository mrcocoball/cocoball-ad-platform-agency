package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.Category;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.QClientUser;
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
public interface ClientUserRepository extends
        JpaRepository<ClientUser, String>,
        QuerydslPredicateExecutor<ClientUser>,
        QuerydslBinderCustomizer<QClientUser> {

    Page<ClientUser> findByAgent_UserIdContaining(String userId, Pageable pageable);

    Page<ClientUser> findByUserIdContaining(String userId, Pageable pageable);

    Page<ClientUser> findByNicknameContaining(String nickname, Pageable pageable);

    Page<ClientUser> findByCategoryContaining(Category category, Pageable pageable);

    List<ClientUser> findByAgent_UserId(String userId);

    @Override
    default void customize(QuerydslBindings bindings, QClientUser root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.userId, root.nickname, root.createdAt, root.createdBy);
        bindings.bind(root.nickname).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
