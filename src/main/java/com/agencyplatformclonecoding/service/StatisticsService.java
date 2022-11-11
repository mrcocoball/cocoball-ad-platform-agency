package com.agencyplatformclonecoding.service;

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
    public List<PerformanceStatisticsDto> getTotalPerformanceStatistics(LocalDate startDate, LocalDate lastDate, Long creativeId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return statisticsQueryRepository.findTotalPerformanceStatisticsByCreativeId(creativeId, startDate, lastDate);
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getCreativeStatistics(LocalDate startDate, LocalDate lastDate, Long campaignId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return statisticsQueryRepository.findCreativeStatisticsByCampaignId(campaignId, startDate, lastDate);
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getTotalCreativeStatistics(LocalDate startDate, LocalDate lastDate, Long campaignId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return statisticsQueryRepository.findTotalCreativeStatisticsByCampaignId(campaignId, startDate, lastDate);
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getCampaignStatistics(LocalDate startDate, LocalDate lastDate, String clientId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return statisticsQueryRepository.findCampaignStatisticsByClientId(clientId, startDate, lastDate);
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getTotalCampaignStatistics(LocalDate startDate, LocalDate lastDate, String clientId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return statisticsQueryRepository.findTotalCampaignStatisticsByClientId(clientId, startDate, lastDate);
    }

    @Transactional(readOnly = true)
    public List<PerformanceStatisticsDto> getTotalSpendStatistics(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return statisticsQueryRepository.findTotalSpendStatistics(startDate, lastDate);
    }

}
