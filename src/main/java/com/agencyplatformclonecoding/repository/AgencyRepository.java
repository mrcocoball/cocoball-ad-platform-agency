package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, String> {
}
