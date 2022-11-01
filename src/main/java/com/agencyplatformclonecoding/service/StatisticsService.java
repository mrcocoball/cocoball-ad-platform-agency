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
    public List<PerformanceStatisticsDto> totalPerformanceStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long creativeId) {

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
            return statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> creativesWithPerformanceStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long campaignId) {

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
            return statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(campaignId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalCreativesPerformanceStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, Long campaignId) {

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
            return statisticsQueryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByCampaign_IdAndTotalStatisticsDefault(campaignId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> clientWithCampaignsStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, String clientId) {

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
            return statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(clientId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(clientId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(clientId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalCampaignsPerformanceStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType, String clientId) {

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
            return statisticsQueryRepository.findByClientUser_IdAndTotalStatisticsDefault(clientId, startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findByClientUser_IdAndTotalStatisticsDefault(clientId, startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findByClientUser_IdAndTotalStatisticsDefault(clientId, startDateBeforeThirtyDays, defaultLastDate);
        };
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> totalSpendStatistics(LocalDate startDate, LocalDate lastDate, StatisticsType statisticsType) {

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
            return statisticsQueryRepository.findClientUserSpendTotalStatisticsDefault(startDate, lastDate);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> statisticsQueryRepository.findClientUserSpendTotalStatisticsDefault(startDateBeforeSevenDays, defaultLastDate);
            case BEFORE_MONTH -> statisticsQueryRepository.findClientUserSpendTotalStatisticsDefault(startDateBeforeThirtyDays, defaultLastDate);
        };
    }

}
