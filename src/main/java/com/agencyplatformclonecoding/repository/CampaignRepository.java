package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.QAgentGroup;
import com.agencyplatformclonecoding.domain.QCampaign;
import com.agencyplatformclonecoding.domain.QClientUser;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CampaignRepository extends
        JpaRepository<Campaign, Long>,
        QuerydslPredicateExecutor<Campaign>,
        QuerydslBinderCustomizer<QCampaign> {

    void deleteById(Long id);

    @Override
    default void customize(QuerydslBindings bindings, QCampaign root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.name, root.createdAt, root.createdBy);
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
