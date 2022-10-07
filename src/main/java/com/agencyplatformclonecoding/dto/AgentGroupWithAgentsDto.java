package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.AgentGroup;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record AgentGroupWithAgentsDto(
        AgencyDto agencyDto,
        Set<AgentDto> agentDtos,
        Long id,
        String name,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static AgentGroupWithAgentsDto of(AgencyDto agencyDto, Set<AgentDto> agentDtos, Long id, String name, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new AgentGroupWithAgentsDto(agencyDto, agentDtos, id, name, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static AgentGroupWithAgentsDto from(AgentGroup entity) {
        return new AgentGroupWithAgentsDto(
                AgencyDto.from(entity.getAgency()),
                entity.getAgents().stream()
                        .map(AgentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
