package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.exception.ErrorCode;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import com.agencyplatformclonecoding.repository.AgentGroupRepository;
import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final AgentGroupRepository agentGroupRepository;
    private final AgencyRepository agencyRepository;
    private final ClientUserRepository clientUserRepository;

    @Transactional(readOnly = true)
    public AgentDto getAgent(String agentId) {
        return agentRepository.findByUserIdAndDeletedFalse(agentId)
                .map(AgentDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.AGENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<AgentWithClientsDto> searchAgents(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return agentRepository.findByDeletedFalse(pageable).map(AgentWithClientsDto::from);
        }

        return switch (searchType) {
            case ID -> agentRepository.findByUserIdContainingAndDeletedFalse(searchKeyword, pageable).map(AgentWithClientsDto::from);
            case NICKNAME -> agentRepository.findByNicknameContainingAndDeletedFalse(searchKeyword, pageable).map(AgentWithClientsDto::from);
        };
    }

    @Transactional(readOnly = true)
    public AgentWithClientsDto getAgentWithClients(String agentId) {
        return agentRepository.findByUserIdAndDeletedFalse(agentId)
                .map(AgentWithClientsDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.AGENT_NOT_FOUND));
    }

    public void deleteAgent(String agentId, String agencyId) {
        try {
            List<ClientUser> clientUsers = clientUserRepository.findByAgent_UserId(agentId);

            if (clientUsers.isEmpty()) {
                agentRepository.setAgentDeletedTrue(agentId, agencyId);
            } else {
                throw new AdPlatformException(ErrorCode.CLIENT_EXISTS);
            }
        } catch (EntityNotFoundException e) {
            log.warn("에이전트를 삭제하지 못하였습니다. 에이전트가 존재하지 않습니다.", e.getLocalizedMessage());
        }
    }

    public long getAgentCount() {
        return agentRepository.countByDeletedFalse();
    }
}
