package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.domain.QCampaign;
import com.agencyplatformclonecoding.domain.QCreative;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CreativeRepository extends
        JpaRepository<Creative, Long>,
        QuerydslPredicateExecutor<Creative>,
        QuerydslBinderCustomizer<QCreative> {

    void deleteById(Long id);

    @Override
    default void customize(QuerydslBindings bindings, QCreative root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.keyword, root.createdAt, root.createdBy);
        bindings.bind(root.keyword).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}

