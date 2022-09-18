package com.agencyplatformclonecoding.service;

import static org.junit.jupiter.api.Assertions.*;

import com.agencyplatformclonecoding.domain.*;
import com.agencyplatformclonecoding.domain.constrant.CampaignStatus;
import com.agencyplatformclonecoding.dto.*;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 캠페인")
@ExtendWith(MockitoExtension.class)
public class CampaignServiceTest {

	@InjectMocks private CampaignService sut;

	@Mock private ClientUserRepository clientUserRepository;
	@Mock private CampaignRepository campaignRepository;

	@DisplayName("READ - 캠페인 조회 시 캠페인 반환")
	@Test
	void givenCampaignId_whenSearchingCampaign_thenReturnsCampaign() {
		// Given
		Long campaignId = 1L;
		Campaign campaign = createCampaign();
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
		given(campaignRepository.findByDeletedFalse(pageable)).willReturn(Page.empty());

		// When
		Page<CampaignDto> campaigns = sut.searchCampaigns(pageable);

		// Then
		assertThat(campaigns).isEmpty();
		then(campaignRepository).should().findByDeletedFalse(pageable);
	}

	@DisplayName("READ - 캠페인 ID 조회 시 캠페인과 편성된 내부 소재 리스트 반환")
	@Test
	void givenCampaignId_whenSearchingCampaignWithCreatives_thenReturnsCampaignWithCreatives() {
		// Given
		Long campaignId = 1L;
		Campaign campaign = createCampaign();
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
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("캠페인이 존재하지 않습니다 - campaignId : " + campaignId);
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
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("캠페인이 존재하지 않습니다 - campaignId : " + campaignId);
		then(campaignRepository).should().findByIdAndDeletedFalse(campaignId);
	}

	@DisplayName("CREATE - 캠페인 정보 입력 시 캠페인 생성")
	@Test
	void givenCampaignInfo_whenSavingCampaign_thenSavesCampaign() {
		// Given
		CampaignDto dto = createCampaignDto();
		given(clientUserRepository.getReferenceById(dto.clientUserDto().userId())).willReturn(createClientUser());
		given(campaignRepository.save(any(Campaign.class))).willReturn(createCampaign());

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
		Agency agency = createAgency();
		Agent agent = createAgent();
		Campaign campaign = createCampaign();
		Category category = createCategory();
		CampaignDto dto = createCampaignDto("update-campaign");
		given(clientUserRepository.getReferenceById(dto.clientUserDto().userId())).willReturn(dto.clientUserDto().toEntity(agency, agent, category));
		given(campaignRepository.getReferenceById(dto.id())).willReturn(campaign);

		// When
		sut.updateCampaign(dto.id(), dto);

		// Then
		assertThat(campaign)
					.hasFieldOrPropertyWithValue("name", dto.name());
		then(clientUserRepository).should().getReferenceById(dto.clientUserDto().userId());
		then(campaignRepository).should().getReferenceById(dto.id());
	}

	@DisplayName("UPDATE - 캠패인 예산 수정 요청 시 캠페인 예산 수정")
	@Test
	void givenCampaignModifiedBudget_whenUpdatingCampaignBudget_thenUpdatesCampaignBudget() {
		// Given
		Agency agency = createAgency();
		Agent agent = createAgent();
		Campaign campaign = createCampaign();
		Category category = createCategory();
		CampaignDto dto = createCampaignDto(30000L);
		given(clientUserRepository.getReferenceById(dto.clientUserDto().userId())).willReturn(dto.clientUserDto().toEntity(agency, agent, category));
		given(campaignRepository.getReferenceById(dto.id())).willReturn(campaign);

		// When
		sut.updateCampaign(dto.id(), dto);

		// Then
		assertThat(campaign)
					.hasFieldOrPropertyWithValue("budget", dto.budget());
		then(clientUserRepository).should().getReferenceById(dto.clientUserDto().userId());
		then(campaignRepository).should().getReferenceById(dto.id());
	}

	@DisplayName("UPDATE - 존재하지 않는 캠페인 수정 요청 시 경고 로그 출력")
	@Test
	void givenNotExistCampaign_whenUpdatingCampaignName_thenLogsWarningAndDoesNothing() {
		// Given
		CampaignDto dto = createCampaignDto("update-campaign");
		given(campaignRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

		// When
		sut.updateCampaign(dto.id(), dto);

		// Then
		then(campaignRepository).should().getReferenceById(dto.id());
	}

	@DisplayName("DELETE - 캠페인 ID 입력 시 캠페인 삭제 - 정상 호출")
	@Test
	void givenCampaignId_whenDeletingCampaign_thenDeletesCampaign() {
		// Given
		Long campaignId = 1L;
		willDoNothing().given(campaignRepository).setCampaignDeletedTrue(campaignId);

		// When
		sut.deleteCampaign(campaignId);

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

	// fixture

	private Agency createAgency() {
		Agency agency = Agency.of(
            "t-agency",
			"pw",
            "테스트용"
		);

		return agency;
	}

	private AgentGroup createAgentGroup() {
		AgentGroup agentGroup = AgentGroup.of(
            createAgency(),
            1L,
            "테스트용그룹"
		);

		return agentGroup;
	}

	private Agent createAgent() {
		Agent agent = Agent.of (
            createAgency(),
            createAgentGroup(),
            "t-agent",
            "pw",
            "email",
            "테스트용"
		);

		return agent;
	}

	private Category createCategory() {
     Category category = Category.of(
             "t-category"
     );

     return category;
 }

	private ClientUser createClientUser() {
		ClientUser clientUser = ClientUser.of(
			createAgency(),
			createAgent(),
			createCategory(),
			"t-client",
			"pw",
			"email",
			"테스트용"
		);

		return clientUser;
	}

	private Campaign createCampaign() {
		Campaign campaign = Campaign.of(
			createClientUser(),
			"t-campaign",
			10000L
		);

		return campaign;
	}

	private AgencyDto createAgencyDto() {
        return AgencyDto.of(
                "t-agency",
				"pw",
                "테스트용"
        );
    }

    private AgentGroupDto createAgentGroupDto() {
        return AgentGroupDto.of(
                createAgencyDto(),
                1L,
                "테스트용",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

	private AgentDto createAgentDto() {
        return AgentDto.of(
                createAgencyDto(),
                createAgentGroupDto(),
                "t-agent",
                "pw",
                "테스트용용",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

	private CategoryDto createCategoryDto() {
     return CategoryDto.of(
             1L,
             "t-category",
             LocalDateTime.now(),
             "test",
             LocalDateTime.now(),
             "test"
        );
     }

	private ClientUserDto createClientUserDto() {
		return ClientUserDto.of(
			createAgencyDto(),
			createAgentDto(),
			createCategoryDto(),
			"t-client",
			"pw",
			"테스트용",
			"email",
			LocalDateTime.now(),
			"테스트용",
			LocalDateTime.now(),
			"테스트용"
		);
	}

	private CampaignDto createCampaignDto() {
		return createCampaignDto("original");
	}

	private CampaignDto createCampaignDto(String name) {
		return CampaignDto.of(
			createClientUserDto(),
			1L,
			name,
			10000L,
			LocalDateTime.now(),
			"테스트용",
			LocalDateTime.now(),
			"테스트용",
			createCampaignStatus()
		);
	}

	private CampaignDto createCampaignDto(Long budget) {
		return CampaignDto.of(
			createClientUserDto(),
			1L,
			"t-campaign",
			budget,
			LocalDateTime.now(),
			"테스트용",
			LocalDateTime.now(),
			"테스트용",
			createCampaignStatus()
		);
	}

	private CampaignStatus createCampaignStatus() {
		CampaignStatus status = new CampaignStatus(100);

		return status;
	}

}
