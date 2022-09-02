package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;
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
        return agentRepository.findById(agentId)
                .map(AgentDto::from)
                .orElseThrow(() -> new EntityNotFoundException("에이전트가 존재하지 않습니다 - agentId : " + agentId));
    }

    @Transactional(readOnly = true)
    public Page<AgentDto> searchAgents(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return agentRepository.findAll(pageable).map(AgentDto::from);
        }

        return switch(searchType) {
            case ID -> agentRepository.findByUserIdContaining(searchKeyword, pageable).map(AgentDto::from);
            case NICKNAME -> agentRepository.findByNicknameContaining(searchKeyword, pageable).map(AgentDto::from);
        };
    }

    @Transactional(readOnly = true)
    public AgentWithClientsDto getAgentWithClients(String agentId) {
        return agentRepository.findById(agentId)
                .map(AgentWithClientsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("에이전트가 존재하지 않습니다 - agentId : " + agentId));
    }

    public void deleteAgent(String agentId) {
        try {
            List<ClientUser> clientUsers = clientUserRepository.findByAgent_UserId(agentId);

            if (clientUsers.isEmpty()) {
                agentRepository.deleteByUserId(agentId);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (EntityNotFoundException e) {
            log.warn("에이전트를 삭제하지 못하였습니다. 에이전트가 존재하지 않습니다.", e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
      			log.warn("매핑된 광고주가 남아 있어 에이전트을 삭제할 수 없습니다. - agentGroupId : {}", e.getLocalizedMessage());
        }
    }

    public long getAgentCount() {
        return agentRepository.count();
    }
}
