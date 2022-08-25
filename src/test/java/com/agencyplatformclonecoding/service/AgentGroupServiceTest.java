package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.AgentGroup;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.*;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import com.agencyplatformclonecoding.repository.AgentGroupRepository;
import com.agencyplatformclonecoding.repository.AgentRepository;
import org.junit.jupiter.api.Disabled;
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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 에이전트 그룹 관리")
@ExtendWith(MockitoExtension.class)
class AgentGroupServiceTest {

	@InjectMocks private AgentGroupService sut;

	@Mock private AgencyRepository agencyRepository;
	@Mock private AgentGroupRepository agentGroupRepository;
	@Mock private AgentRepository agentRepository;

	@DisplayName("READ - 에이전트 그룹 조회 시 에이전트 그룹 반환")
	@Test
	void givenAgentGroupId_whenSearchingAgentGroup_thenReturnsAgentGroup() {
		// Given
		String agentGroupId = "t-group";
		AgentGroup agentGroup = createAgentGroup();
		given(agentGroupRepository.findById(agentGroupId)).willReturn(Optional.of(agentGroup));

		// When
		AgentGroupDto dto = sut.getAgentGroup(agentGroupId);

		// Then
		assertThat(dto)
				.hasFieldOrPropertyWithValue("name", agentGroup.getName());
		then(agentGroupRepository).should().findById(agentGroupId);
	}

	@DisplayName("READ - 검색어 없이 에이전트 그룹 검색 시 에이전트 그룹 페이지 반환")
	@Test
	void givenNoSearchingParameters_whenSearchingAgentGroup_thenReturnsAgentGroupPage() {
		// Given
		Pageable pageable = Pageable.ofSize(20);
		given(agentGroupRepository.findAll(pageable)).willReturn(Page.empty());

		// When
		Page<AgentGroupDto> agentGroups = sut.searchAgentGroups(null, null, pageable);

		// Then
		assertThat(agentGroups).isEmpty();
		then(agentGroupRepository).should().findAll(pageable);
	}

	@DisplayName("READ - 검색어와 함께 에이전트 그룹 검색 시 에이전트 그룹 페이지 반환")
	@Test
	void givenSearchingParameters_whenSearchingAgentGroup_thenReturnsAgentGroupPage() {
		// Given
		SearchType searchType = SearchType.NICKNAME;
		String searchKeyword = "테스트";
		Pageable pageable = Pageable.ofSize(20);
		given(agentGroupRepository.findByNameContaining(searchKeyword, pageable)).willReturn(Page.empty());

		// When
		Page<AgentGroupDto> agentGroups = sut.searchAgentGroups(searchType, searchKeyword, pageable);

		// Then
		assertThat(agentGroups).isEmpty();
		then(agentGroupRepository).should().findByNameContaining(searchKeyword, pageable);
	}

	@DisplayName("READ - 에이전트 그룹 ID 조회 시 에이전트 그룹과 소속된 광고주 리스트 페이지 반환")
	@Test
	void givenAgentGroupId_whenSearchingAgentGroupWithAgents_thenReturnsAgentGroupWithAgents() {
		// Given
		String agentGroupId = "t-group";
		AgentGroup agentGroup = createAgentGroup();
		given(agentGroupRepository.findById(agentGroupId)).willReturn(Optional.of(agentGroup));

		// When
		AgentGroupWithAgentsDto dto = sut.getAgentGroupWithAgents(agentGroupId);

		// Then
		assertThat(dto)
				.hasFieldOrPropertyWithValue("name", agentGroup.getName());
		then(agentGroupRepository).should().findById(agentGroupId);
	}

	@DisplayName("READ - 에이전트 그룹이 없을 경우 예외 처리")
    @Test
    void givenNonexistentAgentGroupId_whenSearchingAgentGroup_thenThrowsException() {
        // Given
        String agentGroupId = "null";
        given(agentGroupRepository.findById(agentGroupId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getAgentGroup(agentGroupId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트 그룹이 존재하지 않습니다 - agentGroupId : " + agentGroupId);
        then(agentGroupRepository).should().findById(agentGroupId);
    }

	@DisplayName("READ - 에이전트가 소속된 에이전트 그룹이 없을 경우 예외 처리")
    @Test
    void givenNonexistentAgentGroupId_whenSearchingAgentGroupWithAgents_thenThrowsException() {
        // Given
        String agentGroupId = "null";
        given(agentGroupRepository.findById(agentGroupId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getAgentGroupWithAgents(agentGroupId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트 그룹이 존재하지 않습니다 - agentGroupId : " + agentGroupId);
        then(agentGroupRepository).should().findById(agentGroupId);
    }

	@DisplayName("CREATE - 에이전트 그룹 정보 입력 시 에이전트 그룹 생성")
    @Test
    void givenAgentGroupInfo_whenSavingAgentGroup_thenSavesAgentGroup() {
        // Given
        AgentGroupDto dto = createAgentGroupDto();
		given(agencyRepository.getReferenceById(dto.agencyDto().agencyId())).willReturn(createAgency());
		given(agentGroupRepository.save(any(AgentGroup.class))).willReturn(createAgentGroup());

        // When
        sut.saveAgentGroup(dto);

        // Then
        then(agencyRepository).should().getReferenceById(dto.agencyDto().agencyId());
		then(agentGroupRepository).should().save(any(AgentGroup.class));
    }

    @DisplayName("CREATE - 에이전트 그룹 ID와 에이전트 정보 입력 시 에이전트 그룹 내 에이전트 생성")
    @Test
    void givenAgentGroupIdAndAgentInfo_whenSavingAgentGroup_thenSavesAgentGroup() {
        // Given
        AgentDto dto = createAgentDto();
   		String agentGroupId = "t-group";
   		given(agencyRepository.getReferenceById(dto.agencyDto().agencyId())).willReturn(createAgency());
   		given(agentGroupRepository.getReferenceById(dto.agentGroupDto().id())).willReturn(createAgentGroup());
   		given(agentRepository.save(any(Agent.class))).willReturn(createAgent());

        // When
        sut.saveAgentInAgentGroup(agentGroupId, dto);

        // Then
        then(agencyRepository).should().getReferenceById(dto.agencyDto().agencyId());
   	    then(agentGroupRepository).should().getReferenceById(dto.agentGroupDto().id());
   		then(agentRepository).should().save(any(Agent.class));
    }

	@DisplayName("UPDATE - 에이전트 그룹 이름 수정 정보 입력 시 에이전트 그룹 이름 수정")
    @Test
    void givenAgentGroupModifiedName_whenUpdatingAgentGroupName_thenUpdatesAgentGroupName() {
        // Given
		AgentGroup agentGroup = createAgentGroup();
		AgentGroupDto dto = createAgentGroupDto("update-group");
		given(agentGroupRepository.getReferenceById(dto.id())).willReturn(agentGroup);
		given(agencyRepository.getReferenceById(dto.agencyDto().agencyId())).willReturn(dto.agencyDto().toEntity());

        // When
        sut.updateAgentGroup(dto.id(), dto);

        // Then
		assertThat(agentGroup)
				.hasFieldOrPropertyWithValue("name", dto.name());
        then(agentGroupRepository).should().getReferenceById(dto.id());
        then(agencyRepository).should().getReferenceById(dto.agencyDto().agencyId());
    }

	@DisplayName("UPDATE - 존재하지 않는 에이전트 그룹을 수정하려고 할 경우 경고 로그 출력")
    @Test
    void givenNotExistAgentGroup_whenUpdatingAgentGroupName_thenLogsWarningAndDoesNothing() {
        // Given
		AgentGroupDto dto = createAgentGroupDto("update-group");
		given(agentGroupRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateAgentGroup(dto.id(), dto);

        // Then
		then(agentGroupRepository).should().getReferenceById(dto.id());
    }

	@DisplayName("DELETE - 에이전트 그룹의 ID 입력 시 에이전트 그룹을 삭제 - 정상 호출(소속 에이전트 없는 경우)")
    @Test
    void givenAgentGroupId_whenDeletingAgentGroup_thenDeleteAgentGroup() {
		// Given
        String agentGroupId = "t-group";
        willDoNothing().given(agentGroupRepository).deleteAgentGroupById(agentGroupId);

        // When
        sut.deleteAgentGroup(agentGroupId);

        // Then
        then(agentGroupRepository).should().deleteAgentGroupById(agentGroupId);
    }

    @Disabled("")
	@DisplayName("DELETE - 존재하지 않는 에이전트 그룹의 ID로 삭제 요청 시 경고 로그 출력")
    @Test
    void givenNotExistAgentGroupId_whenDeletingAgentGroup_thenLogsWarningAndDoesNothing() {
		// Given
        String agentGroupId = "t-group";
        // given(agentGroupRepository).deleteAgentGroupById(agentGroupId).willThrow(EntityNotFoundException.class);

        // When
        sut.deleteAgentGroup(agentGroupId);

        // Then
        then(agentGroupRepository).should().deleteAgentGroupById(agentGroupId);
    }

    @Disabled("")
	@DisplayName("DELETE - 에이전트가 남아 있는 에이전트 그룹 ID로 삭제 요청 시 경고 로그 출력")
    @Test
    void givenHavingAgentsAgentGroupId_whenDeletingAgentGroup_thenLogsWarningAndDoesNothing() {
		// Given
        String agentGroupId = "t-group";
        // given(agentGroupRepository).deleteAgentGroupById(agentGroupId).willThrow(IllegalArgumentException.class);

        // When
        sut.deleteAgentGroup(agentGroupId);

        // Then
        then(agentGroupRepository).should().deleteById(agentGroupId);
    }

	@DisplayName("READ - 에이전트 그룹 수를 조회하면, 에이전트 그룹 수를 반환한다")
    @Test
    void givenNothing_whenCountingAgentGroups_thenReturnsAgentGroupCount() {
        // Given
        long expected = 0L;
        given(agentGroupRepository.count()).willReturn(expected);

        // When
        long actual = sut.getAgentGroupCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(agentGroupRepository).should().count();
    }

	@DisplayName("READ - 에이전트 그룹 내부 에이전트 수를 조회하면, 에이전트 수를 반환한다")
    @Test
    void givenAgentGroupId_whenCountingAgentsInAgentGroup_thenReturnsAgentCount() {
        // Given
		String agentGroupId = "t-group";
        given(agentRepository.findByAgentGroup_Id(agentGroupId)).willReturn(Collections.emptyList());
        int expected = 0;

        // When
        int actual = sut.getAgentCount(agentGroupId);

        // Then
        assertThat(actual).isEqualTo(expected);
		then(agentRepository).should().findByAgentGroup_Id(agentGroupId);
    }

	// fixture

 private Agency createAgency() {
     Agency agency = Agency.of(
             "t-agency",
             "테스트용"
     );

     return agency;
 }

 private AgentGroup createAgentGroup() {
     AgentGroup agentGroup = AgentGroup.of(
             createAgency(),
             "t-group",
             "테스트용그룹"
     );

     return agentGroup;
 }

 private Agent createAgent() {
     Agent agent = Agent.of(
             createAgency(),
             createAgentGroup(),
             "t-agent",
             "pw",
             "email",
             "테스트용"
     );

     return agent;
 }

 private ClientUser createClientUser() {
     ClientUser clientUser = ClientUser.of(
             createAgency(),
             createAgent(),
             "t-client",
             "pw",
             "email",
             "테스트용"
     );

     return clientUser;
 }

 private List<ClientUser> clientUsers() {
     List<ClientUser> clientUsers = Arrays.asList(createClientUser());

     return clientUsers;
 }

 private AgencyDto createAgencyDto() {
     return AgencyDto.of(
             "t-agency",
             "테스트용"
     );
 }

 private AgentGroupDto createAgentGroupDto() {
        return createAgentGroupDto("update-group");
 }

 private AgentGroupDto createAgentGroupDto(String agentGroupName) {
     return AgentGroupDto.of(
             createAgencyDto(),
             "t-group",
             agentGroupName,
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

 private AgentWithClientsDto createAgentWithClientsDto() {
     return AgentWithClientsDto.of(
             createAgencyDto(),
             createAgentGroupDto(),
             Set.of(),
             "t-agent",
             "pw",
             "김테스트",
             "email",
             LocalDateTime.now(),
             "테스트",
             LocalDateTime.now(),
             "테스트"
     );
 }

}