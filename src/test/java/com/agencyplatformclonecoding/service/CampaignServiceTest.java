package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.Category;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.CampaignWithCreativesDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.fixture.Fixture;
import com.agencyplatformclonecoding.repository.CampaignRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 캠페인")
@ExtendWith(MockitoExtension.class)
public class CampaignServiceTest {

    @InjectMocks
    private CampaignService sut;

    @Mock
    private ClientUserRepository clientUserRepository;
    @Mock
    private CampaignRepository campaignRepository;

    @DisplayName("READ - 캠페인 조회 시 캠페인 반환")
    @Test
    void givenCampaignId_whenSearchingCampaign_thenReturnsCampaign() {
        // Given
        Long campaignId = 1L;
        Campaign campaign = Fixture.createCampaign();
        given(campaignRepository.findByIdAndDeletedFalse(campaignId)).willReturn(Optional.of(campaign));

        // When
        CampaignDto dto = sut.getCampaign(campaignId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("name", campaign.getName())
                .hasFieldOrPropertyWithValue("budget", campaign.getBudget());
        then(campaignRepository).should().findByIdAndDeletedFalse(campaignId);
    }

    @DisplayName("READ - 캠페인 리스트 조회 시 캠페인 리스트 반환")
    @Test
    void givenNothing_whenSearchingCampaigns_thenReturnsCmpaigns() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        String clientId = "t-client";
        given(campaignRepository.findByDeletedFalseAndClientUser_UserId(pageable, clientId)).willReturn(Page.empty());

        // When
        Page<CampaignDto> campaigns = sut.searchCampaigns(pageable, clientId);

        // Then
        assertThat(campaigns).isEmpty();
        then(campaignRepository).should().findByDeletedFalseAndClientUser_UserId(pageable, clientId);
    }

    @DisplayName("READ - 캠페인 ID 조회 시 캠페인과 편성된 내부 소재 리스트 반환")
    @Test
    void givenCampaignId_whenSearchingCampaignWithCreatives_thenReturnsCampaignWithCreatives() {
        // Given
        Long campaignId = 1L;
        Campaign campaign = Fixture.createCampaign();
        given(campaignRepository.findByIdAndDeletedFalse(campaignId)).willReturn(Optional.of(campaign));

        // When
        CampaignWithCreativesDto dto = sut.getCampaignWithCreatives(campaignId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("name", campaign.getName())
                .hasFieldOrPropertyWithValue("budget", campaign.getBudget());
        then(campaignRepository).should().findByIdAndDeletedFalse(campaignId);
    }

    @DisplayName("READ - 캠페인이 존재하지 않을 경우 예외 처리")
    @Test
    void givenNonexistentCampaignId_whenSearchingCampaign_thenThrowsException() {
        // Given
        Long campaignId = 0L;
        given(campaignRepository.findByIdAndDeletedFalse(campaignId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getCampaign(campaignId));

        // Then
        assertThat(t)
                .isInstanceOf(AdPlatformException.class);
        then(campaignRepository).should().findByIdAndDeletedFalse(campaignId);
    }

    @DisplayName("READ - 소재가 등록된 캠페인이 존재하지 않을 경우 예외 처리")
    @Test
    void givenNonexistentCampaignId_whenSearchingCampaignWithCreatives_thenThrowsException() {
        // Given
        Long campaignId = 0L;
        given(campaignRepository.findByIdAndDeletedFalse(campaignId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getCampaignWithCreatives(campaignId));

        // Then
        assertThat(t)
                .isInstanceOf(AdPlatformException.class);
        then(campaignRepository).should().findByIdAndDeletedFalse(campaignId);
    }

    @DisplayName("CREATE - 캠페인 정보 입력 시 캠페인 생성")
    @Test
    void givenCampaignInfo_whenSavingCampaign_thenSavesCampaign() {
        // Given
        CampaignDto dto = Fixture.createCampaignDto();
        given(clientUserRepository.getReferenceById(dto.clientUserDto().userId())).willReturn(Fixture.createClientUser());
        given(campaignRepository.save(any(Campaign.class))).willReturn(Fixture.createCampaign());

        // When
        sut.saveCampaign(dto);

        // Then
        then(clientUserRepository).should().getReferenceById(dto.clientUserDto().userId());
        then(campaignRepository).should().save(any(Campaign.class));
    }

    @DisplayName("UPDATE - 캠패인 이름 수정 요청 시 캠페인 이름 수정")
    @Test
    void givenCampaignModifiedName_whenUpdatingCampaignName_thenUpdatesCampaignName() {
        // Given
        Agency agency = Fixture.createAgency();
        Agent agent = Fixture.createAgent();
        Campaign campaign = Fixture.createCampaign();
        Category category = Fixture.createCategory();
        CampaignDto dto = Fixture.createCampaignDto("update-campaign");
        given(clientUserRepository.getReferenceById(dto.clientUserDto().userId())).willReturn(dto.clientUserDto().toEntity(agency, agent, category));
        given(campaignRepository.getReferenceById(dto.id())).willReturn(campaign);

        // When
        sut.updateCampaign(dto.id(), dto.clientUserDto().userId(), dto);

        // Then
        assertThat(campaign)
                .hasFieldOrPropertyWithValue("name", dto.name());
        then(clientUserRepository).should(atLeast(1)).getReferenceById(dto.clientUserDto().userId());
        then(campaignRepository).should(atLeast(1)).getReferenceById(dto.id());
    }

    @DisplayName("UPDATE - 캠패인 예산 수정 요청 시 캠페인 예산 수정")
    @Test
    void givenCampaignModifiedBudget_whenUpdatingCampaignBudget_thenUpdatesCampaignBudget() {
        // Given
        Agency agency = Fixture.createAgency();
        Agent agent = Fixture.createAgent();
        Campaign campaign = Fixture.createCampaign();
        Category category = Fixture.createCategory();
        CampaignDto dto = Fixture.createCampaignDto(30000L);
        given(clientUserRepository.getReferenceById(dto.clientUserDto().userId())).willReturn(dto.clientUserDto().toEntity(agency, agent, category));
        given(campaignRepository.getReferenceById(dto.id())).willReturn(campaign);

        // When
        sut.updateCampaign(dto.id(), dto.clientUserDto().userId(), dto);

        // Then
        assertThat(campaign)
                .hasFieldOrPropertyWithValue("budget", dto.budget());
        then(clientUserRepository).should(atLeast(1)).getReferenceById(dto.clientUserDto().userId());
        then(campaignRepository).should(atLeast(1)).getReferenceById(dto.id());
    }

    @DisplayName("UPDATE - 존재하지 않는 캠페인 수정 요청 시 경고 로그 출력")
    @Test
    void givenNotExistCampaign_whenUpdatingCampaignName_thenLogsWarningAndDoesNothing() {
        // Given
        CampaignDto dto = Fixture.createCampaignDto("update-campaign");
        given(campaignRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateCampaign(dto.id(), dto.clientUserDto().userId(), dto);

        // Then
        then(campaignRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("DELETE - 캠페인 ID 입력 시 캠페인 삭제 - 정상 호출")
    @Test
    void givenCampaignId_whenDeletingCampaign_thenDeletesCampaign() {
        // Given
        Long campaignId = 1L;
        String clientId = "t-test";
        willDoNothing().given(campaignRepository).setCampaignDeletedTrue(campaignId);

        // When
        sut.deleteCampaign(campaignId, clientId);

        // Then
        then(campaignRepository).should().setCampaignDeletedTrue(campaignId);
    }

    @DisplayName("READ - 캠페인 수를 조회할 경우 캠페인 수를 반환")
    @Test
    void givenNothing_whenCountingCampaigns_thenReturnsCampaignCount() {
        // Given
        long expected = 0L;
        given(campaignRepository.countByDeletedFalse()).willReturn(expected);

        // When
        long actual = sut.getCampaignCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(campaignRepository).should().countByDeletedFalse();
    }
}
