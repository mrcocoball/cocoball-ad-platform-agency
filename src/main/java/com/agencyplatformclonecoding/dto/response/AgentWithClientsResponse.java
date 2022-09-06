package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.AgentWithClientsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record AgentWithClientsResponse(
        String userId,
        LocalDateTime createdAt,
        String nickname,
        Set<ClientUserResponse> clientUserResponses,
        String agentGroupName
) implements Serializable {

    public static AgentWithClientsResponse of(String userId, LocalDateTime createdAt, String nickname, Set<ClientUserResponse> clientUserResponses, String agentGroupName) {
        return new AgentWithClientsResponse(userId, createdAt, nickname, clientUserResponses, agentGroupName);
    }

    public static AgentWithClientsResponse from(AgentWithClientsDto dto) {
        String agentGroupName = dto.agentGroupDto().name();

        return new AgentWithClientsResponse(
                dto.userId(),
                dto.createdAt(),
                dto.nickname(),
                dto.clientUserDtos().stream()
                        .map(ClientUserResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                agentGroupName
        );
    }

}
