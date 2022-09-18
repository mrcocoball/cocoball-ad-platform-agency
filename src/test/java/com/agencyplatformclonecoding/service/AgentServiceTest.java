package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.*;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import com.agencyplatformclonecoding.repository.AgentGroupRepository;
import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 에이전트 관리")
@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

    @InjectMocks
    private AgentService sut;

    @Mock
    private AgentRepository agentRepository;
    @Mock
    private AgentGroupRepository agentGroupRepository;
    @Mock
    private AgencyRepository agencyRepository;
    @Mock
    private ClientUserRepository clientUserRepository;

    @DisplayName("READ - 에이전트 조회 시 에이전트 반환")
    @Test
    void givenAgentId_whenSearchingAgent_thenReturnsAgent() {
        // Given
        String agentId = "t-agent";
        Agent agent = createAgent();
        given(agentRepository.findByUserIdAndDeletedFalse(agentId)).willReturn(Optional.of(agent));

        // When
        AgentDto dto = sut.getAgent(agentId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("userId", agent.getUserId())
                .hasFieldOrPropertyWithValue("nickname", agent.getNickname());
        then(agentRepository).should().findByUserIdAndDeletedFalse(agentId);
    }

    @DisplayName("READ - 검색어 없이 검색 시 에이전트 리스트를 반환")
    @Test
    void givenNoSearchingParameter_whenSearchingAgents_thenReturnsAgentPage() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(agentRepository.findByDeletedFalse(pageable)).willReturn(Page.empty());

        // When
        Page<AgentDto> agents = sut.searchAgents(null, null, pageable);

        // Then
        assertThat(agents).isEmpty();
        then(agentRepository).should().findByDeletedFalse(pageable);
    }

    @DisplayName("READ - 검색어를 통해 에이전트 검색 시 에이전트 리스트를 반환")
    @Test
    void givenSearchingParameter_whenSearchingAgents_thenReturnsAgentPage() {
        // Given
        SearchType searchType = SearchType.ID;
        String searchKeyword = "t-agent";
        Pageable pageable = Pageable.ofSize(20);
        given(agentRepository.findByUserIdContainingAndDeletedFalse(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<AgentDto> agents = sut.searchAgents(searchType, searchKeyword, pageable);

        // Then
        assertThat(agents).isEmpty();
        then(agentRepository).should().findByUserIdContainingAndDeletedFalse(searchKeyword, pageable);
    }

    @DisplayName("READ - 에이전트 조회 시 에이전트에 매핑된 광고주 리스트 반환")
    @Test
    void givenAgentId_whenSearchingAgentWithClients_thenReturnsAgentWithClients() {
        // Given
        String agentId = "t-agent";
        Agent agent = createAgent();
        given(agentRepository.findByUserIdAndDeletedFalse(agentId)).willReturn(Optional.of(agent));

        // When
        AgentWithClientsDto dto = sut.getAgentWithClients(agentId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("userId", agent.getUserId())
                .hasFieldOrPropertyWithValue("nickname", agent.getNickname());
        then(agentRepository).should().findByUserIdAndDeletedFalse(agentId);
    }

    @DisplayName("READ - 광고주를 매핑한 에이전트가 없을 경우 예외 처리")
    @Test
    void givenNonexistentAgentId_whenSearchingAgentWithClients_thenReturnsAgentWithClients() {
        // Given
        String agentId = "none-agent";
        given(agentRepository.findByUserIdAndDeletedFalse(agentId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getAgentWithClients(agentId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트가 존재하지 않습니다 - agentId : " + agentId);
        then(agentRepository).should().findByUserIdAndDeletedFalse(agentId);
    }

    @DisplayName("READ - 에이전트가 없을 경우 예외 처리")
    @Test
    void givenNonexistentAgentId_whenSearchingAgent_thenThrowsException() {
        // Given
        String agentId = "none-agent";
        given(agentRepository.findByUserIdAndDeletedFalse(agentId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getAgent(agentId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트가 존재하지 않습니다 - agentId : " + agentId);
        then(agentRepository).should().findByUserIdAndDeletedFalse(agentId);
    }

    @DisplayName("DELETE - 에이전트 ID 입력 시 에이전트를 삭제 - 정상 (매핑된 광고주가 없을 시)")
    @Test
    void givenNotMappingClientsAgentId_whenDeletingAgent_thenDeleteAgent() {
        // Given
        String agentId = "test";
        String agencyId = "t-agency";
        willDoNothing().given(agentRepository).setAgentDeletedTrue(agentId, agencyId);

        // When
        sut.deleteAgent(agentId, agencyId);

        // Then
        then(agentRepository).should().setAgentDeletedTrue(agentId, agencyId);
    }

    @DisplayName("READ - 에이전트 수를 조회하면, 에이전트 수를 반환한다")
    @Test
    void givenNothing_whenCountingAgents_thenReturnsAgentsCount() {
        // Given
        long expected = 0L;
        given(agentRepository.countByDeletedFalse()).willReturn(expected);

        // When
        long actual = sut.getAgentCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(agentRepository).should().countByDeletedFalse();
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

    private List<ClientUser> clientUsers() {
        List<ClientUser> clientUsers = Arrays.asList(createClientUser());

        return clientUsers;
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

    private AgentGroupDto createModifiedAgentGroupDto(Long agentGroupId) {
        return AgentGroupDto.of(
                createAgencyDto(),
                agentGroupId,
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

    private AgentDto createAgentDto(Long agentGroupId) {
        return AgentDto.of(
                createAgencyDto(),
                createModifiedAgentGroupDto(agentGroupId),
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