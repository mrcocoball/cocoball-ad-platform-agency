package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.ClientUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ClientUserResponse(
        String userId,
        String categoryName,
        LocalDateTime createdAt,
        String nickname,
        String email,
        String agentId,
        String agentName
) implements Serializable {

    public static ClientUserResponse of(String userId, String categoryName, LocalDateTime createdAt, String nickname, String email, String agentId, String agentName) {
        return new ClientUserResponse(userId, categoryName, createdAt, nickname, email, agentId, agentName);
    }

    public static ClientUserResponse from(ClientUserDto dto) {
        String agentName = dto.agentDto().nickname();
        if (agentName == null || agentName.isBlank()) {
            agentName = dto.agentDto().userId();
        }

        String categoryName = dto.categoryDto().name();

        return new ClientUserResponse(
                dto.userId(),
                categoryName,
                dto.createdAt(),
                dto.nickname(),
                dto.email(),
                dto.agentDto().userId(),
                agentName
        );
    }
}
