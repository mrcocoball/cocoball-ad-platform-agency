package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.constrant.StatisticsType;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.repository.QueryRepository;
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

    private final QueryRepository queryRepository;

    // List 타입 반환
    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalPerformanceStatistics(StatisticsType statisticsType, Long creativeId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return queryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> queryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> queryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

    // List 타입 반환
    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> creativesWithPerformanceStatistics(StatisticsType statisticsType, Long campaignId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return queryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> queryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> queryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
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
            return queryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> queryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeSevenDays, lastDate);
            case BEFORE_MONTH -> queryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeThirtyDays, lastDate);
            case BEFORE_CUSTOM -> null;
        };
    }

}
