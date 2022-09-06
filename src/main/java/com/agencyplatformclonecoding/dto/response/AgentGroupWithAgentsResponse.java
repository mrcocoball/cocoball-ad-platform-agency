package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.AgentGroupWithAgentsDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record AgentGroupWithAgentsResponse(
        Long id,
        LocalDateTime createdAt,
        String name,
        Set<AgentResponse> agentResponses
) implements Serializable {

    public static AgentGroupWithAgentsResponse of(Long id, LocalDateTime createdAt, String name, Set<AgentResponse> agentResponses) {
        return new AgentGroupWithAgentsResponse(id, createdAt, name, agentResponses);
    }

    public static AgentGroupWithAgentsResponse from(AgentGroupWithAgentsDto dto) {

        return new AgentGroupWithAgentsResponse(
                dto.id(),
                dto.createdAt(),
                dto.name(),
                dto.agentDtos().stream()
                        .map(AgentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
