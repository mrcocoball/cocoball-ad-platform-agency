package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.domain.constrant.StatisticsType;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.PerformanceDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.exception.ErrorCode;
import com.agencyplatformclonecoding.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PerformanceStatisticsDto totalPerformanceStatistics(StatisticsType statisticsType, Long creativeId) {

        LocalDate lastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = lastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = lastDate.minusDays(30);

        if (statisticsType == null) {
            return queryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, lastDate).get(0);
        }

        return switch (statisticsType) {
            case BEFORE_WEEK -> queryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeSevenDays, lastDate).get(0);
            case BEFORE_MONTH -> queryRepository.findByCreative_IdAndStatisticsDefault(creativeId, startDateBeforeThirtyDays, lastDate).get(0);
            case BEFORE_CUSTOM -> null;
        };
    }

}
