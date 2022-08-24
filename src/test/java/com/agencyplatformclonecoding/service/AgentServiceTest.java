package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.AgentGroup;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import com.agencyplatformclonecoding.repository.AgentGroupRepository;
import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 에이전트 관리")
@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

    @InjectMocks private AgentService sut;

    @Mock private AgentRepository agentRepository;
    @Mock private AgentGroupRepository agentGroupRepository;
    @Mock private AgencyRepository agencyRepository;
    @Mock private ClientUserRepository clientUserRepository;

    @DisplayName("READ - 에이전트 조회 시 에이전트 반환")
    @Test
    void givenAgentId_whenSearchingAgent_thenReturnsAgent() {
        // Given
        String agentId = "t-agent";
        Agent agent = createAgent();
        given(agentRepository.findById(agentId)).willReturn(Optional.of(agent));

        // When
        AgentDto dto = sut.getAgent(agentId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("userId", agent.getUserId())
                .hasFieldOrPropertyWithValue("nickname", agent.getNickname());
        then(agentRepository).should().findById(agentId);
    }

    @DisplayName("READ - 검색어 없이 검색 시 에이전트 리스트를 반환")
    @Test
    void givenNoSearchingParameter_whenSearchingAgents_thenReturnsAgentPage() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(agentRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<AgentDto> agents = sut.searchAgents(null, null, pageable);

        // Then
        assertThat(agents).isEmpty();
        then(agentRepository).should().findAll(pageable);
    }

    @DisplayName("READ - 검색어를 통해 에이전트 검색 시 에이전트 리스트를 반환")
    @Test
    void givenSearchingParameter_whenSearchingAgents_thenReturnsAgentPage() {
        // Given
        SearchType searchType = SearchType.ID;
        String searchKeyword = "t-agent";
        Pageable pageable = Pageable.ofSize(20);
        given(agentRepository.findByUserIdContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<AgentDto> agents = sut.searchAgents(searchType, searchKeyword, pageable);

        // Then
        assertThat(agents).isEmpty();
        then(agentRepository).should().findByUserIdContaining(searchKeyword, pageable);
    }

    @DisplayName("READ - 에이전트 조회 시 에이전트에 매핑된 광고주 리스트 반환")
    @Test
    void givenAgentId_whenSearchingAgentWithClients_thenReturnsAgentWithClients() {
        // Given
        String agentId = "t-agent";
        Agent agent = createAgent();
        given(agentRepository.findById(agentId)).willReturn(Optional.of(agent));

        // When
        AgentWithClientsDto dto = sut.getAgentWithClients(agentId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("userId", agent.getUserId())
                .hasFieldOrPropertyWithValue("nickname", agent.getNickname());
        then(agentRepository).should().findById(agentId);
    }

    @DisplayName("READ - 광고주를 매핑한 에이전트가 없을 경우 예외 처리")
    @Test
    void givenNonexistentAgentId_whenSearchingAgentWithClients_thenReturnsAgentWithClients() {
        // Given
        String agentId = "none-agent";
        given(agentRepository.findById(agentId)).willReturn(Optional.empty());

        // When
        Throwable t= catchThrowable(() -> sut.getAgentWithClients(agentId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트가 존재하지 않습니다 - agentId : " + agentId);
        then(agentRepository).should().findById(agentId);
    }

    @DisplayName("READ - 에이전트가 없을 경우 예외 처리")
    @Test
    void givenNonexistentAgentId_whenSearchingAgent_thenThrowsException() {
        // Given
        String agentId = "none-agent";
        given(agentRepository.findById(agentId)).willReturn(Optional.empty());

        // When
        Throwable t= catchThrowable(() -> sut.getAgent(agentId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트가 존재하지 않습니다 - agentId : " + agentId);
        then(agentRepository).should().findById(agentId);
    }

    @DisplayName("DELETE - 에이전트 ID 입력 시 에이전트를 삭제 - 정상 (매핑된 광고주가 없을 시)")
    @Test
    void givenNotMappingClientsAgentId_whenDeletingAgent_thenDeleteAgent() {
        // Given
        String agentId = "test";
        willDoNothing().given(agentRepository).deleteAgentByUserId(agentId);

        // When
        sut.deleteAgent(agentId);

        // Then
        then(agentRepository).should().deleteAgentByUserId(agentId);
    }

    @Disabled("에이전트-광고주 매핑 관계를 테스트에 넣는 방법 확인 필요")
    @DisplayName("DELETE - 에이전트 ID 입력 시 에이전트를 삭제 - 예외 처리 (매핑된 광고주가 있을 경우)")
    @Test
    void givenMappingClientsAgentId_whenDeletingAgent_thenThrowsException() {
        // Given
        Agent agent = createAgent();
        String agentId = agent.getUserId();
        List<ClientUser> clientUsers = clientUsers();
        given(clientUserRepository.findByAgent_UserId(agentId)).willReturn(clientUsers);
        willDoNothing().given(agentRepository).deleteAgentByUserId(agentId);

        // When
        Throwable t = catchThrowable(() -> sut.deleteAgent(agentId));

        // Then
        assertThat(t)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("매핑된 광고주가 있어 삭제가 불가능합니다 - agentId : " + agentId);
        then(agentRepository).should().deleteAgentByUserId(agentId);
    }

    @Disabled("에이전트-광고주 매핑 관계를 테스트에 넣는 방법 확인 필요")
    @DisplayName("DELETE - 에이전트 ID 입력 시 에이전트를 삭제 - 예외 처리 (존재하지 않는 에이전트 ID)")
    @Test
    void givenWrongClientsAgentId_whenDeletingAgent_thenThrowsException() {
        // Given
        String agentId = "none-agent";
        given(agentRepository.findById(agentId)).willReturn(Optional.empty());
        willDoNothing().given(agentRepository).deleteAgentByUserId(agentId);

        // When
        Throwable t= catchThrowable(() -> sut.deleteAgent(agentId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트가 존재하지 않습니다 - agentId : " + agentId);
        then(agentRepository).should().deleteAgentByUserId(agentId);
    }

    @DisplayName("READ - 에이전트 수를 조회하면, 에이전트트 수를반환한다")
    @Test
    void givenNothing_whenCountingAgents_thenReturnsAgentsCount() {
        // Given
        long expected = 0L;
        given(agentRepository.count()).willReturn(expected);

        // When
        long actual = sut.getAgentCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(agentRepository).should().count();
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
        return AgentGroupDto.of(
                createAgencyDto(),
                "t-group",
                "테스트용",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    private AgentGroupDto createModifiedAgentGroupDto(String agentGroupId) {
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

    private AgentDto createAgentDto(String agentGroupId) {
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