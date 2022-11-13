package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.fixture.Fixture;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import com.agencyplatformclonecoding.repository.AgentGroupRepository;
import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

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
        Agent agent = Fixture.createAgent();
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
        Page<AgentWithClientsDto> agents = sut.searchAgents(null, null, pageable);

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
        Page<AgentWithClientsDto> agents = sut.searchAgents(searchType, searchKeyword, pageable);

        // Then
        assertThat(agents).isEmpty();
        then(agentRepository).should().findByUserIdContainingAndDeletedFalse(searchKeyword, pageable);
    }

    @DisplayName("READ - 에이전트 조회 시 에이전트에 매핑된 광고주 리스트 반환")
    @Test
    void givenAgentId_whenSearchingAgentWithClients_thenReturnsAgentWithClients() {
        // Given
        String agentId = "t-agent";
        Agent agent = Fixture.createAgent();
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
                .isInstanceOf(AdPlatformException.class);
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
                .isInstanceOf(AdPlatformException.class);
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
}