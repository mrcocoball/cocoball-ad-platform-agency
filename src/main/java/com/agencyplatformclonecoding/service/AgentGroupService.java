package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.AgentGroup;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;
import com.agencyplatformclonecoding.dto.AgentGroupWithAgentsDto;
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
public class AgentGroupService {

    private final AgencyRepository agencyRepository;
    private final AgentGroupRepository agentGroupRepository;
    private final AgentRepository agentRepository;

    @Transactional(readOnly = true)
    public AgentGroupDto getAgentGroup(Long agentGroupId) {
        return agentGroupRepository.findById(agentGroupId)
                .map(AgentGroupDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.AGENT_GROUP_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public AgentGroupWithAgentsDto getAgentGroupWithAgents(Long agentGroupId) {
        return agentGroupRepository.findById(agentGroupId)
                .map(AgentGroupWithAgentsDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.AGENT_GROUP_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<AgentGroupWithAgentsDto> searchAgentGroups(Pageable pageable) {

        return agentGroupRepository.findAll(pageable).map(AgentGroupWithAgentsDto::from);
    }

    public void saveAgentGroup(AgentGroupDto dto) {
        Agency agency = agencyRepository.getReferenceById(dto.agencyDto().agencyId());

        if (!isValidGroupNameLength(dto.name())) {
            throw new AdPlatformException(ErrorCode.BAD_REQUEST_AGENT_GROUP_NAME);
        }

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

                    if (!isValidGroupNameLength(dto.name())) {
                        throw new AdPlatformException(ErrorCode.BAD_REQUEST_AGENT_GROUP_NAME);
                    }

                    agentGroup.setName(dto.name());
                }
        } catch (EntityNotFoundException e) {
            log.warn("???????????? ????????? ??????????????? ????????? ????????? ?????? ??? ????????????. - dto : {}", e.getLocalizedMessage());
        }
    }

    public void deleteAgentGroup(Long agentGroupId, String agencyId) {

        try {
            AgentGroup agentGroup = agentGroupRepository.getReferenceById(agentGroupId);
            List<Agent> agents = agentRepository.findByAgentGroup_IdAndDeletedFalse(agentGroupId);

            if (agents.size() == 0) {
                agentGroupRepository.deleteByIdAndAgency_AgencyId(agentGroupId, agencyId);
            } else {
                throw new AdPlatformException(ErrorCode.AGENT_EXISTS);
            }
        } catch (EntityNotFoundException e) {
            log.warn("???????????? ????????? ??????????????? ????????? ????????? ?????? ??? ????????????. - agentGroupId : {}", e.getLocalizedMessage());
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

    public boolean isValidGroupNameLength(String string) {
        return string.length() <= 20;
    }
}
