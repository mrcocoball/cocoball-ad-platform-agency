package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agent;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record AgentWithClientsDto(
        AgencyDto agencyDto,
        AgentGroupDto agentGroupDto,
        Set<ClientUserDto> clientUserDtos,
        String userId,
        String userPassword,
        String nickname,
        String email,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static AgentWithClientsDto of(AgencyDto agencyDto, AgentGroupDto agentGroupDto, Set<ClientUserDto> clientUserDtos, String userId, String password, String nickname, String email, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new AgentWithClientsDto(agencyDto, agentGroupDto, clientUserDtos, userId, password, nickname, email, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static AgentWithClientsDto from(Agent entity) {
        return new AgentWithClientsDto(
                AgencyDto.from(entity.getAgency()),
                AgentGroupDto.from(entity.getAgentGroup()),
                entity.getClientUsers().stream()
                        .map(ClientUserDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
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

}
