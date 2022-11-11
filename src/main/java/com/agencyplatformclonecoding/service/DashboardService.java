package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.repository.DashboardQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DashboardService {

    // 일일 소진액 차트 출력
    private final DashboardQueryRepository dashboardQueryRepository;

    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setSpendChart(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findSpendChartData(startDate, lastDate).stream().limit(31).collect(Collectors.toList());
    }

    // 일일 소진액 리스트 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setSpendChartList(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        List<DashboardStatisticsDto> resultList = dashboardQueryRepository.findSpendChartList(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 상위 광고주별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setClientsSpendChart(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findClientsSpendChartDataAndList(startDate, lastDate).stream().limit(10).collect(Collectors.toList());
    }

    // 전체 광고주별 소진액 리스트 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setClientsSpendChartList(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        List<DashboardStatisticsDto> resultList = dashboardQueryRepository.findClientsSpendChartDataAndList(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 상위 에이전트별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setAgentsSpendChart(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findAgentsSpendChartDataAndList(startDate, lastDate).stream().limit(10).collect(Collectors.toList());
    }

    // 전체 에이전트별 소진액 페이지 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setAgentsSpendChartList(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        List<DashboardStatisticsDto> resultList = dashboardQueryRepository.findAgentsSpendChartDataAndList(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 에이전트 그룹별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setGroupsSpendChart(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findGroupsSpendChartDataAndList(startDate, lastDate);
    }

    // 전체 에이전트 그룹별 소진액 페이지 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setGroupsSpendChartList(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        List<DashboardStatisticsDto> resultList = dashboardQueryRepository.findGroupsSpendChartDataAndList(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 업종별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setCategoryChart(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findCategoryChartDataAndList(startDate, lastDate);
    }

    // 업종별 소진액 페이지 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setCategoryChartList(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        List<DashboardStatisticsDto> resultList = dashboardQueryRepository.findCategoryChartDataAndList(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 업종별 실적 지표 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setReferenceChart(LocalDate startDate, LocalDate lastDate) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findReferenceChartDataAndList(startDate, lastDate);
    }

    // 업종별 실적 지표 페이지 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setReferenceChartList(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        List<DashboardStatisticsDto> resultList = dashboardQueryRepository.findReferenceChartDataAndList(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 캠페인 실적 지표 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setCampaignPerformanceChart(LocalDate startDate, LocalDate lastDate, String clientId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findCampaignPerformanceChartData(clientId, startDate, lastDate);
    }

    // 소재 실적 지표 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setCreativePerformanceChart(LocalDate startDate, LocalDate lastDate, Long campaignId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findCreativePerformanceChartData(campaignId, startDate, lastDate);
    }

    // 상세 실적 지표 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setPerformanceChart(LocalDate startDate, LocalDate lastDate, Long creativeId) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);

        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeSevenDays;
        }

        return dashboardQueryRepository.findPerformanceChartData(creativeId, startDate, lastDate);
    }
}
