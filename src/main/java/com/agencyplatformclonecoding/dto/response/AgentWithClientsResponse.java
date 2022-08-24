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
        Set<ClientUserResponse> clientUserResponses
) implements Serializable {

    public static AgentWithClientsResponse of (String userId, LocalDateTime createdAt, String nickname, Set<ClientUserResponse> clientUserResponses) {
        return new AgentWithClientsResponse(userId, createdAt, nickname, clientUserResponses);
    }

    public static AgentWithClientsResponse from(AgentWithClientsDto dto) {
        String nickname = dto.nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userId();
        }

        return new AgentWithClientsResponse(
                dto.userId(),
                dto.createdAt(),
                nickname,
                dto.clientUserDtos().stream()
                        .map(ClientUserResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
