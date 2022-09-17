package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.AgentGroup;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;
import com.agencyplatformclonecoding.dto.AgentGroupWithAgentsDto;
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
public class AgentGroupService {

    private final AgencyRepository agencyRepository;
    private final AgentGroupRepository agentGroupRepository;
    private final AgentRepository agentRepository;

    @Transactional(readOnly = true)
    public AgentGroupDto getAgentGroup(Long agentGroupId) {
        return agentGroupRepository.findById(agentGroupId)
                .map(AgentGroupDto::from)
                .orElseThrow(() -> new EntityNotFoundException("에이전트 그룹이 존재하지 않습니다 - agentGroupId : " + agentGroupId));
    }

    @Transactional(readOnly = true)
    public AgentGroupWithAgentsDto getAgentGroupWithAgents(Long agentGroupId) {
        return agentGroupRepository.findById(agentGroupId)
                .map(AgentGroupWithAgentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("에이전트 그룹이 존재하지 않습니다 - agentGroupId : " + agentGroupId));
    }

    @Transactional(readOnly = true)
    public Page<AgentGroupDto> searchAgentGroups(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return agentGroupRepository.findAll(pageable).map(AgentGroupDto::from);
        }

        return switch (searchType) {
            case ID -> agentGroupRepository.findByIdContaining(searchKeyword, pageable).map(AgentGroupDto::from);
            case NICKNAME -> agentGroupRepository.findByNameContaining(searchKeyword, pageable).map(AgentGroupDto::from);
        };
    }

    public void saveAgentGroup(AgentGroupDto dto) {
        Agency agency = agencyRepository.getReferenceById(dto.agencyDto().agencyId());
        agentGroupRepository.save(dto.toEntity(agency));
    }

    public void saveAgentInAgentGroup(Long agentGroupId, AgentDto dto) {
        Agency agency = agencyRepository.getReferenceById(dto.agencyDto().agencyId());
        AgentGroup agentGroup = agentGroupRepository.getReferenceById(dto.agentGroupDto().id());
        agentRepository.save(dto.toEntity(agency, agentGroup));
    }

    public void updateAgentGroup(Long agentGroupId, AgentGroupDto dto) {

        try {
            AgentGroup agentGroup = agentGroupRepository.getReferenceById(agentGroupId);
            Agency agency = agencyRepository.getReferenceById(dto.agencyDto().agencyId());

            if (agentGroup.getAgency().equals(agency))
                if (dto.name() != null) {
                    agentGroup.setName(dto.name());
                }
        } catch (EntityNotFoundException e) {
            log.warn("에이전트 그룹을 수정하는데 필요한 정보를 찾을 수 없습니다. - dto : {}", e.getLocalizedMessage());
        }
    }

    public void deleteAgentGroup(Long agentGroupId, String agencyId) {

        try {
            AgentGroup agentGroup = agentGroupRepository.getReferenceById(agentGroupId);
            List<Agent> agents = agentRepository.findByAgentGroup_IdAndDeletedFalse(agentGroupId);

            if (agents.size() == 0) {
                agentGroupRepository.deleteByIdAndAgency_AgencyId(agentGroupId, agencyId);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (EntityNotFoundException e) {
            log.warn("에이전트 그룹을 삭제하는데 필요한 정보를 찾을 수 없습니다. - agentGroupId : {}", e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            log.warn("소속된 에이전트가 남아 있어 에이전트 그룹을 삭제할 수 없습니다. - agentGroupId : {}", e.getLocalizedMessage());
        }
    }

    public long getAgentGroupCount() {
        return agentGroupRepository.count();
    }

    public int getAgentCount(Long agentGroupId) {
        List<Agent> agents = agentRepository.findByAgentGroup_IdAndDeletedFalse(agentGroupId);
        if (agents != null) {
            return agents.size();
        } else {
            return 0;
        }
    }
}
