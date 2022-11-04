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
    public List<PerformanceStatisticsDto> getTotalPerformanceStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long creativeId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (statisticsType == null) {
            return statisticsQueryRepository.findTotalPerformanceStatisticsByCreativeId(creativeId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findTotalPerformanceStatisticsByCreativeId(creativeId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findTotalPerformanceStatisticsByCreativeId(creativeId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getCreativeStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long campaignId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);


        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (statisticsType == null) {
            return statisticsQueryRepository.findCreativeStatisticsByCampaignId(campaignId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findCreativeStatisticsByCampaignId(campaignId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findCreativeStatisticsByCampaignId(campaignId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getTotalCreativeStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long campaignId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (statisticsType == null) {
            return statisticsQueryRepository.findTotalCreativeStatisticsByCampaignId(campaignId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findTotalCreativeStatisticsByCampaignId(campaignId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findTotalCreativeStatisticsByCampaignId(campaignId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getCampaignStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, String clientId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (statisticsType == null) {
            return statisticsQueryRepository.findCampaignStatisticsByClientId(clientId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findCampaignStatisticsByClientId(clientId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findCampaignStatisticsByClientId(clientId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getTotalCampaignStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, String clientId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (statisticsType == null) {
            return statisticsQueryRepository.findTotalCampaignStatisticsByClientId(clientId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findTotalCampaignStatisticsByClientId(clientId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findTotalCampaignStatisticsByClientId(clientId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getTotalSpendStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (statisticsType == null) {
            return statisticsQueryRepository.findTotalSpendStatistics(startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findTotalSpendStatistics(startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findTotalSpendStatistics(startDateBeforeThirtyDays, defaultLastDate);
        };
    }

}
