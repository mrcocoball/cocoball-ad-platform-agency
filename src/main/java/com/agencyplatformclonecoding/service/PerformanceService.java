package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.domain.constrant.StatisticsType;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PerformanceService {

    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;
    private final ClientUserRepository clientUserRepository;
    private final PerformanceRepository performanceRepository;
    private final StatisticsQueryRepository statisticsQueryRepository;

    @Transactional(readOnly = true)
    public PerformanceDto getPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId)
                .map(PerformanceDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.PERFORMANCE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<PerformanceDto> searchPerformances(Pageable pageable, LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long creativeId, Long campaignId, String clientId) {

        validateClientAndCampaignAndCreative(creativeId, campaignId, clientId);
        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (statisticsType == null) {
            return performanceRepository.findByCreative_IdAndCreatedAtBetween(pageable, creativeId, startDate, lastDate).map(PerformanceDto::from);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> performanceRepository.findByCreative_IdAndCreatedAtBetween(pageable, creativeId, startDateBeforeSevenDays, defaultLastDate).map(PerformanceDto::from);
            case BEFORE_MONTH -> performanceRepository.findByCreative_IdAndCreatedAtBetween(pageable, creativeId, startDateBeforeThirtyDays, defaultLastDate).map(PerformanceDto::from);
        };
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

        if (creative == null) {
            throw new AdPlatformException(ErrorCode.CREATIVE_NOT_FOUND);
        }

        if (campaign == null) {
            throw new AdPlatformException(ErrorCode.CAMPAIGN_NOT_FOUND);
        }

        if (clientUser == null) {
            throw new AdPlatformException(ErrorCode.CLIENT_NOT_FOUND);
        }

        if (!campaign.getClientUser().equals(clientUser)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }

        if (!creative.getCampaign().equals(campaign)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }
    }

}

