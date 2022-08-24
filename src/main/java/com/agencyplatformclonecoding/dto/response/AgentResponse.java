package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.AgentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AgentResponse (
        String userId,
        LocalDateTime createdAt,
        String nickname,
        String email
) implements Serializable {

    public static AgentResponse of(String userId, LocalDateTime createdAt, String nickname, String email) {
        return new AgentResponse(userId, createdAt, nickname, email);
    }

    public static AgentResponse from(AgentDto dto) {
        String nickname = dto.nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userId();
        }
        return new AgentResponse(
                dto.userId(),
                dto.createdAt(),
                nickname,
                dto.email()
        );
    }
}
