package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.constrant.StatisticsType;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.repository.StatisticsQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class StatisticsService {

    private final StatisticsQueryRepository statisticsQueryRepository;

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalPerformanceStatistics(StatisticsType statisticsType, Long creativeId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> creativesWithPerformanceStatistics(StatisticsType statisticsType, Long campaignId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalCreativesPerformanceStatistics(StatisticsType statisticsType, Long campaignId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return statisticsQueryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> clientWithCampaignsStatistics(StatisticsType statisticsType, String clientId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(clientId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(clientId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(clientId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalCampaignsPerformanceStatistics(StatisticsType statisticsType, String clientId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return statisticsQueryRepository.findByClientUser_IdAndTotalStatisticsDefault(clientId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByClientUser_IdAndTotalStatisticsDefault(clientId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByClientUser_IdAndTotalStatisticsDefault(clientId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalSpendStatistics(StatisticsType statisticsType) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return statisticsQueryRepository.findClientUserSpendTotalStatisticsDefault(startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findClientUserSpendTotalStatisticsDefault(startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findClientUserSpendTotalStatisticsDefault(startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

}
