package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.AgentGroup;
import com.agencyplatformclonecoding.domain.QAgency;
import com.agencyplatformclonecoding.domain.QAgentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface AgencyRepository extends JpaRepository<Agency, String> {
}
