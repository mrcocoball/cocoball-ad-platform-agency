package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.AgentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AgentResponse(
        String userId,
        LocalDateTime createdAt,
        String nickname,
        String email,
        String agentGroupName
) implements Serializable {

    public static AgentResponse of(String userId, LocalDateTime createdAt, String nickname, String email, String agentGroupName) {
        return new AgentResponse(userId, createdAt, nickname, email, agentGroupName);
    }

    public static AgentResponse from(AgentDto dto) {
        String agentGroupName = dto.agentGroupDto().name();

        return new AgentResponse(
                dto.userId(),
                dto.createdAt(),
                dto.nickname(),
                dto.email(),
                agentGroupName
        );
    }
}
