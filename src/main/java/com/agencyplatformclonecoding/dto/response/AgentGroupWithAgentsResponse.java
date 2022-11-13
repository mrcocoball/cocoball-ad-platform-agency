package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.AgentGroupWithAgentsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record AgentGroupWithAgentsResponse(
        Long id,
        LocalDateTime createdAt,
        String name,
        Set<AgentResponse> agentResponses,
        int agentsCount
) implements Serializable {

    public static AgentGroupWithAgentsResponse of(Long id, LocalDateTime createdAt, String name, Set<AgentResponse> agentResponses, int agentsCount) {
        return new AgentGroupWithAgentsResponse(id, createdAt, name, agentResponses, agentsCount);
    }

    public static AgentGroupWithAgentsResponse from(AgentGroupWithAgentsDto dto) {

        return new AgentGroupWithAgentsResponse(
                dto.id(),
                dto.createdAt(),
                dto.name(),
                dto.agentDtos().stream()
                        .map(AgentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                dto.agentsCount()
        );
    }

}
