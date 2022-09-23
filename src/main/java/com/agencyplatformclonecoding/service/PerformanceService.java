package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.dto.ClientUserWithCampaignsDto;
import com.agencyplatformclonecoding.dto.PerformanceDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.exception.ErrorCode;
import com.agencyplatformclonecoding.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PerformanceService {

    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;
    private final ClientUserRepository clientUserRepository;
	private final PerformanceRepository performanceRepository;

    @Transactional(readOnly = true)
    public PerformanceDto getPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId)
                .map(PerformanceDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.PERFORMANCE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<PerformanceDto> searchPerformances(Pageable pageable, Long creativeId, Long campaignId, String clientId) {
        validateClientAndCampaignAndCreative(creativeId, campaignId, clientId);
        return performanceRepository.findByCreative_Id(pageable, creativeId).map(PerformanceDto::from);
    }

    public long getPerformanceCount() {
        return performanceRepository.count();
    }

    public void validateClientAndCampaign(Long campaignId, String clientId) {

        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        ClientUser clientUser = clientUserRepository.getReferenceById(clientId);

        if (!campaign.getClientUser().equals(clientUser)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }
    }

    public void validateClientAndCampaignAndCreative(Long creativeId, Long campaignId, String clientId) {

        Creative creative = creativeRepository.getReferenceById(creativeId);
        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        ClientUser clientUser = clientUserRepository.getReferenceById(clientId);

        if (!campaign.getClientUser().equals(clientUser)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }

        if (!creative.getCampaign().equals(campaign)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }
    }

}

