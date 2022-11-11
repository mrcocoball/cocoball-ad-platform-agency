package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.dto.PerformanceDto;
import com.agencyplatformclonecoding.repository.CampaignRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import com.agencyplatformclonecoding.repository.CreativeRepository;
import com.agencyplatformclonecoding.repository.PerformanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 실적")
@ExtendWith(MockitoExtension.class)
class PerformanceServiceTest {

    @InjectMocks
    private PerformanceService sut;

    @Mock private ClientUserRepository clientUserRepository;
    @Mock private CampaignRepository campaignRepository;
    @Mock private CreativeRepository creativeRepository;
    @Mock private PerformanceRepository performanceRepository;


    @DisplayName("READ - 소재 실적 기간 조회 시 실적 리스트 / 통계 반환")
    @Test
    void givenNothing_whenSearchingCreatives_thenReturnsCreatives() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        Long campaignId = 1L;
        String clientId = "t-client";
        Long creativeId = 1L;
        LocalDate lastDate = LocalDate.now().minusDays(1);
        LocalDate startDate = lastDate.minusDays(6);
        given(performanceRepository.findByCreative_IdAndCreatedAtBetween(pageable, creativeId, startDate, lastDate)).willReturn(Page.empty());

        // When
        Page<PerformanceDto> performances = sut.searchPerformances(pageable, startDate, lastDate, creativeId, campaignId, clientId);

        // Then
        assertThat(performances).isEmpty();
        then(performanceRepository).should().findByCreative_IdAndCreatedAtBetween(pageable, creativeId, startDate, lastDate);
    }

    @DisplayName("READ - 실적 건수를 조회할 경우 실적 건수를 반환")
    @Test
    void givenNothing_whenCountingPerformances_thenReturnsPerformanceCount() {
        // Given
        long expected = 0L;
        given(performanceRepository.count()).willReturn(expected);

        // When
        long actual = sut.getPerformanceCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(performanceRepository).should().count();
    }

}