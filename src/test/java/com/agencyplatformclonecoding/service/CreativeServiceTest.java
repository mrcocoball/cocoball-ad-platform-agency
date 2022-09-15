package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.*;
import com.agencyplatformclonecoding.domain.constrant.CampaignStatus;
import com.agencyplatformclonecoding.domain.constrant.CreativeStatus;
import com.agencyplatformclonecoding.dto.*;
import com.agencyplatformclonecoding.repository.CampaignRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import com.agencyplatformclonecoding.repository.CreativeRepository;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

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
public class CreativeServiceTest {

	@InjectMocks private CreativeService sut;

	@Mock private ClientUserRepository clientUserRepository;
	@Mock private CampaignRepository campaignRepository;
	@Mock private CreativeRepository creativeRepository;

	@DisplayName("READ - 소재 조회 시 소재 반환")
	@Test
	void givenCreativeId_whenSearchingCreative_thenReturnsCreative() {
		// Given
		Long creativeId = 1L;
		Creative creative = createCreative();
		given(creativeRepository.findById(creativeId)).willReturn(Optional.of(creative));

		// When
		CreativeDto dto = sut.getCreative(creativeId);

		// Then
		assertThat(dto)
					.hasFieldOrPropertyWithValue("keyword", creative.getKeyword())
					.hasFieldOrPropertyWithValue("bidingPrice", creative.getBidingPrice());
		then(creativeRepository).should().findById(creativeId);
	}

	@DisplayName("READ - 소재 리스트 조회 시 소재 리스트 반환")
	@Test
	void givenNothing_whenSearchingCreatives_thenReturnsCreatives() {
		// Given
		Pageable pageable = Pageable.ofSize(20);
		given(creativeRepository.findAll(pageable)).willReturn(Page.empty());

		// When
		Page<CreativeDto> creatives = sut.searchCreatives(pageable);

		// Then
		assertThat(creatives).isEmpty();
		then(creativeRepository).should().findAll(pageable);
	}

	@DisplayName("READ - 소재가 존재하지 않을 경우 예외 처리")
	@Test
	void givenNonexistentCreativeId_whenSearchingCreative_thenThrowsException() {
		// Given
		Long creativeId = 0L;
		given(creativeRepository.findById(creativeId)).willReturn(Optional.empty());

		// When
		Throwable t = catchThrowable(() -> sut.getCreative(creativeId));

		// Then
		assertThat(t)
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("소재가 존재하지 않습니다 - creativeId : " + creativeId);
		then(creativeRepository).should().findById(creativeId);
	}

	@DisplayName("CREATE - 소재 정보 입력 시 소재 생성")
	@Test
	void givenCreativeInfo_whenSavingCreative_thenSavesCreative() {
		// Given
		CreativeDto dto = createCreativeDto();
		given(campaignRepository.getReferenceById(dto.campaignDto().id())).willReturn(createCampaign());
		given(creativeRepository.save(any(Creative.class))).willReturn(createCreative());

		// When
		sut.saveCreative(dto);

		// Then
		then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
		then(creativeRepository).should().save(any(Creative.class));
	}

	@DisplayName("CREATE - 소재 생성을 시도하였으나 일치하는 캠페인이 없을 경우 경고 로그 출력")
	@Test
	void givenNotExistCreative_whenSavingCreative_thenLogsWarningAndDoesNothing() {
		// Given
		CreativeDto dto = createCreativeDto("update-campaign");
		given(campaignRepository.getReferenceById(dto.campaignDto().id())).willThrow(EntityNotFoundException.class);

		// When
		sut.saveCreative(dto);

		// Then
		then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
		then(creativeRepository).shouldHaveNoInteractions();
	}

	@DisplayName("UPDATE - 소재 키워드 수정 요청 시 소재 키워드 수정")
	@Test
	void givenCreativeModifiedKeyword_whenUpdatingCreativeKeyword_thenUpdatesCreativeKeyword() {
		// Given
		ClientUser clientUser = createClientUser();
		Creative creative = createCreative();
		CreativeDto dto = createCreativeDto("t-keyword");
		given(campaignRepository.getReferenceById(dto.campaignDto().id())).willReturn(dto.campaignDto().toEntity(clientUser));
		given(creativeRepository.getReferenceById(dto.id())).willReturn(creative);

		// When
		sut.updateCreative(dto.id(), dto);

		// Then
		assertThat(creative)
					.hasFieldOrPropertyWithValue("keyword", dto.keyword());
		then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
		then(creativeRepository).should().getReferenceById(dto.id());
	}

	@DisplayName("UPDATE - 소재 입찰가 수정 요청 시 소재 입찰가 수정")
	@Test
	void givenCreativeModifiedBidingPrice_whenUpdatingCreativeBidingPrice_thenUpdatesCreativeBidingPrice() {
		// Given
		ClientUser clientUser = createClientUser();
		Creative creative = createCreative();
		CreativeDto dto = createCreativeDto(2000L);
		given(campaignRepository.getReferenceById(dto.campaignDto().id())).willReturn(dto.campaignDto().toEntity(clientUser));
		given(creativeRepository.getReferenceById(dto.id())).willReturn(creative);

		// When
		sut.updateCreative(dto.id(), dto);

		// Then
		assertThat(creative)
					.hasFieldOrPropertyWithValue("bidingPrice", dto.bidingPrice());
		then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
		then(creativeRepository).should().getReferenceById(dto.id());
	}

	@DisplayName("UPDATE - 존재하지 않는 소재 수정 요청 시 경고 로그 출력")
	@Test
	void givenNotExistCreative_whenUpdatingCreativeKeyword_thenLogsWarningAndDoesNothing() {
		// Given
		CreativeDto dto = createCreativeDto("update-campaign");
		given(creativeRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

		// When
		sut.updateCreative(dto.id(), dto);

		// Then
		then(creativeRepository).should().getReferenceById(dto.id());
	}

	@DisplayName("DELETE - 소재 ID, 캠페인 ID 입력 시 소재 삭제 - 정상 호출")
	@Test
	void givenCreativeId_whenDeletingCreative_thenDeletesCreative() {
		// Given
		Long creativeId = 1L;
		willDoNothing().given(creativeRepository).deleteById(creativeId);

		// When
		sut.deleteCreative(creativeId);

		// Then
		then(creativeRepository).should().deleteById(creativeId);
	}

	@DisplayName("READ - 캠페인 수를 조회할 경우 캠페인 수를 반환")
	@Test
	void givenNothing_whenCountingCreatives_thenReturnsCreativeCount() {
		// Given
		long expected = 0L;
		given(creativeRepository.count()).willReturn(expected);

		// When
		long actual = sut.getCreativeCount();

		// Then
		assertThat(actual).isEqualTo(expected);
		then(creativeRepository).should().count();
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

	private Creative createCreative() {
		Creative creative = Creative.of(
			createCampaign(),
			"t-keyword",
			1000L
		);

		return creative;
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

	private CreativeDto createCreativeDto() {
		return createCreativeDto("original");
	}

	private CreativeDto createCreativeDto(String keyword) {
		return CreativeDto.of(
			createCampaignDto(),
			1L,
			keyword,
			1000L
		);
	}

	private CreativeDto createCreativeDto(Long bidingPrice) {
		return CreativeDto.of(
			createCampaignDto(),
			1L,
			"t-creative",
			bidingPrice
		);
	}

	private CampaignStatus createCampaignStatus() {
		CampaignStatus status = new CampaignStatus(100);

		return status;
	}

	private CreativeStatus createCreativeStatus() {
		CreativeStatus status = new CreativeStatus(100);

		return status;
	}

}
