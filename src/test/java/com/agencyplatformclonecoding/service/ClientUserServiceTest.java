package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.ClientUserDto;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 광고주 관리")
@ExtendWith(MockitoExtension.class)
class ClientUserServiceTest {

    @InjectMocks
    private ClientUserService sut;

    @Mock
    private AgentRepository agentRepository;
    @Mock
    private AgentGroupRepository agentGroupRepository;
    @Mock
    private AgencyRepository agencyRepository;
    @Mock
    private ClientUserRepository clientUserRepository;

    @DisplayName("READ - 광고주 조회 시 광고주 반환")
    @Test
    void givenAgentId_whenSearchingClientUser_thenReturnsClientUser() {
        // Given
        String clientId = "t-client";
        ClientUser clientUser = Fixture.createClientUser();
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
        Throwable t = catchThrowable(() -> sut.getClientUser(clientId));

        // Then
        assertThat(t)
                .isInstanceOf(AdPlatformException.class);
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
}