package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.ClientUser;

import java.time.LocalDateTime;

import static com.agencyplatformclonecoding.domain.QAgency.agency;

public record ClientUserDto(
        AgencyDto agencyDto,
        AgentDto agentDto,
        String userId,
        String userPassword,
        String nickname,
        String email,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ClientUserDto of(AgencyDto agencyDto, AgentDto agentDto, String password, String nickname, String email) {
        return new ClientUserDto(agencyDto, agentDto, null, password, nickname, email, null, null, null, null);
    }

    public static ClientUserDto of(String userId) {
        return new ClientUserDto(null, null, userId, null, null, null, null, null, null, null);
    }

    public static ClientUserDto of(AgencyDto agencyDto, AgentDto agentDto, String userId, String password, String nickname, String email, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ClientUserDto(agencyDto, agentDto, userId, password, nickname, email, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static ClientUserDto from(ClientUser entity) {
        return new ClientUserDto(
                AgencyDto.from(entity.getAgency()),
                AgentDto.from(entity.getAgent()),
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getNickname(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // dto -> Entity로 변환
    public ClientUser toEntity(Agency agency, Agent agent) {
        return ClientUser.of(
                agency,
                agent,
                userId,
                userPassword,
                nickname,
                email
        );
    }
}
