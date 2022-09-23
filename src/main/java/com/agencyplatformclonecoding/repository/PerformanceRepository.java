package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Performance;
import com.agencyplatformclonecoding.domain.QPerformance;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

public interface PerformanceRepository extends
        JpaRepository<Performance, Long>,
        QuerydslPredicateExecutor<Performance>,
        QuerydslBinderCustomizer<QPerformance> {

    Page<Performance> findByCreative_Id(Pageable pageable, Long creativeId);

    Optional<Performance> findByCreative_Id(Long creativeId);

    long countByCreative_Id(Long creativeId);

    @Override
    default void customize(QuerydslBindings bindings, QPerformance root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.createdAt);
        bindings.bind(root.createdAt).first(DateExpression::eq);
    }
}
