package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, String> {
}
