package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.repository.StatisticsQueryRepository;
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

    private final StatisticsQueryRepository statisticsQueryRepository;

    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setTestDashboard(LocalDate startDate, LocalDate lastDate) {

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

        return statisticsQueryRepository.dashboardTestQuery(startDate, lastDate).stream().limit(31).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setTestDashboardTable(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

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

        List<DashboardStatisticsDto> resultList = statisticsQueryRepository.dashboardTestQuery2(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 상위 광고주별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setTestDashboard2(LocalDate startDate, LocalDate lastDate) {

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

        return statisticsQueryRepository.dashboardTestQuery3(startDate, lastDate).stream().limit(10).collect(Collectors.toList());
    }

    // 전체 광고주별 소진액 차트 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setTestDashboardTable2(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

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

        List<DashboardStatisticsDto> resultList = statisticsQueryRepository.dashboardTestQuery3(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 상위 에이전트별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setTestDashboard3(LocalDate startDate, LocalDate lastDate) {

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

        return statisticsQueryRepository.dashboardTestQuery4(startDate, lastDate).stream().limit(10).collect(Collectors.toList());
    }

    // 전체 에이전트별 소진액 페이지 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setTestDashboardTable3(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

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

        List<DashboardStatisticsDto> resultList = statisticsQueryRepository.dashboardTestQuery4(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }

    // 에이전트 그룹별 소진액 차트 출력
    @Transactional(readOnly = true)
    public List<DashboardStatisticsDto> setTestDashboard4(LocalDate startDate, LocalDate lastDate) {

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

        return statisticsQueryRepository.dashboardTestQuery5(startDate, lastDate);
    }

    // 전체 에이전트별 소진액 페이지 출력
    @Transactional(readOnly = true)
    public Page<DashboardStatisticsDto> setTestDashboardTable4(LocalDate startDate, LocalDate lastDate, Pageable pageable) {

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

        List<DashboardStatisticsDto> resultList = statisticsQueryRepository.dashboardTestQuery5(startDate, lastDate);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), resultList.size());
        final Page<DashboardStatisticsDto> resultPage = new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        return resultPage;
    }
}
