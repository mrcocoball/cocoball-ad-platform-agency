package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.*;
import com.agencyplatformclonecoding.dto.CampaignDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CampaignRepository extends
        JpaRepository<Campaign, Long>,
        QuerydslPredicateExecutor<Campaign>,
        QuerydslBinderCustomizer<QCampaign> {

    Page<Campaign> findByDeletedFalseAndClientUser_UserId(Pageable pageable, String clientId);

    Optional<Campaign> findByIdAndDeletedFalse(Long campaignId);

    long countByDeletedFalse();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Campaign c SET c.deleted = true where c.id = :id")
    void setCampaignDeletedTrue(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Campaign c SET c.activated = false where c.id = :id")
    void setCampaignDisabled(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Campaign c SET c.activated = true where c.id = :id")
    void setCampaignActivated(@Param("id") Long id);

    @Override
    default void customize(QuerydslBindings bindings, QCampaign root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.name, root.createdAt, root.createdBy);
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
