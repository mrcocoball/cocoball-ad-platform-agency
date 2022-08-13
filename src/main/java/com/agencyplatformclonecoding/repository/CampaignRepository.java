package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
