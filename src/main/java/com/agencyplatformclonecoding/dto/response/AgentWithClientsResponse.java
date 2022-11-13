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
        String email,
        Set<ClientUserResponse> clientUserResponses,
        int clientsCount,
        String agentGroupName
) implements Serializable {

    public static AgentWithClientsResponse of(String userId, LocalDateTime createdAt, String nickname, String email, Set<ClientUserResponse> clientUserResponses, int clientsCount, String agentGroupName) {
        return new AgentWithClientsResponse(userId, createdAt, nickname, email, clientUserResponses, clientsCount, agentGroupName);
    }

    public static AgentWithClientsResponse from(AgentWithClientsDto dto) {
        String agentGroupName = dto.agentGroupDto().name();

        return new AgentWithClientsResponse(
                dto.userId(),
                dto.createdAt(),
                dto.nickname(),
                dto.email(),
                dto.clientUserDtos().stream()
                        .map(ClientUserResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                dto.clientsCount(),
                agentGroupName
        );
    }

}
