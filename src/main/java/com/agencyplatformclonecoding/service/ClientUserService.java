package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.exception.ErrorCode;
import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ClientUserService {

    private final ClientUserRepository clientUserRepository;

    @Transactional(readOnly = true)
    public ClientUserDto getClientUser(String clientId) {
        return clientUserRepository.findById(clientId)
                .map(ClientUserDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.CLIENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<ClientUserDto> searchClientUsers(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return clientUserRepository.findAll(pageable).map(ClientUserDto::from);
        }

        return switch (searchType) {
            case ID -> clientUserRepository.findByUserIdContaining(searchKeyword, pageable).map(ClientUserDto::from);
            case NICKNAME -> clientUserRepository.findByNicknameContaining(searchKeyword, pageable).map(ClientUserDto::from);
        };
    }

    public long getClientUserCount() {
        return clientUserRepository.count();
    }
}
