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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 광고주 관리")
@ExtendWith(MockitoExtension.class)
class ClientUserServiceTest {

    @InjectMocks
    private ClientUserService sut;

    @Mock
    private AgentRepository agentRepository;
    @Mock private AgentGroupRepository agentGroupRepository;
    @Mock private AgencyRepository agencyRepository;
    @Mock private ClientUserRepository clientUserRepository;

    @DisplayName("READ - 광고주 조회 시 광고주 반환")
    @Test
    void givenAgentId_whenSearchingClientUser_thenReturnsClientUser() {
        // Given
        String clientId = "t-client";
        ClientUser clientUser = createClientUser();
        given(clientUserRepository.findById(clientId)).willReturn(Optional.of(clientUser));

        // When
        ClientUserDto dto = sut.getClientUser(clientId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("userId", clientUser.getUserId())
                .hasFieldOrPropertyWithValue("nickname", clientUser.getNickname());
        then(clientUserRepository).should().findById(clientId);
    }

    @DisplayName("READ - 검색어 없이 검색 시 광고주 리스트를 반환")
    @Test
    void givenNoSearchingParameter_whenSearchingClientUsers_thenReturnsClientUserPage() {
        // Given
        Pageable pageable = Pageable.ofSize(100);
        given(clientUserRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<ClientUserDto> clientUsers = sut.searchClientUsers(null, null, pageable);

        // Then
        assertThat(clientUsers).isEmpty();
        then(clientUserRepository).should().findAll(pageable);
    }

    @DisplayName("READ - 검색어를 통해 광고주 검색 시 광고주 리스트를 반환")
    @Test
    void givenSearchingParameter_whenSearchingClients_thenReturnsClientsPage() {
        // Given
        SearchType searchType = SearchType.ID;
        String searchKeyword = "t-client";
        Pageable pageable = Pageable.ofSize(100);
        given(clientUserRepository.findByUserIdContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ClientUserDto> clientUsers = sut.searchClientUsers(searchType, searchKeyword, pageable);

        // Then
        assertThat(clientUsers).isEmpty();
        then(clientUserRepository).should().findByUserIdContaining(searchKeyword, pageable);
    }

    @DisplayName("READ - 광고주가 없을 경우 예외 처리")
    @Test
    void givenNonexistentClientUserId_whenSearchingClientUser_thenThrowsException() {
        // Given
        String clientId = "none-client";
        given(clientUserRepository.findById(clientId)).willReturn(Optional.empty());

        // When
        Throwable t= catchThrowable(() -> sut.getClientUser(clientId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("에이전트가 존재하지 않습니다 - clientId : " + clientId);
        then(clientUserRepository).should().findById(clientId);
    }

    @DisplayName("READ - 광고주 수를 조회하면, 광고주 수를 반환한다")
    @Test
    void givenNothing_whenCountingClientUsers_thenReturnsClientUsersCount() {
        // Given
        long expected = 0L;
        given(clientUserRepository.count()).willReturn(expected);

        // When
        long actual = sut.getClientUserCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(clientUserRepository).should().count();
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