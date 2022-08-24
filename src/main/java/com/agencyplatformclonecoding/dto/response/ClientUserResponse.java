package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.ClientUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ClientUserResponse(
        String clientId,
        LocalDateTime createdAt,
        String nickname,
        String email
) implements Serializable {

    public static ClientUserResponse of (String clientId, LocalDateTime createdAt, String nickname, String email) {
        return new ClientUserResponse(clientId, createdAt, nickname, email);
    }

    public static ClientUserResponse from (ClientUserDto dto) {
        String nickname = dto.nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userId();
        }

        return new ClientUserResponse(
                dto.userId(),
                dto.createdAt(),
                nickname,
                dto.email()
        );
    }
}
