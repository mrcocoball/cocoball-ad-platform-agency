package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.Agent;
import com.agencyplatformclonecoding.domain.AgentGroup;

import java.time.LocalDateTime;

public record AgentDto(

        AgencyDto agencyDto,
        AgentGroupDto agentGroupDto,
        String userId,
        String userPassword,
        String nickname,
        String email,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static AgentDto of(AgencyDto agencyDto, AgentGroupDto agentGroupDto, String userPassword, String nickname, String email) {
        return new AgentDto(agencyDto, agentGroupDto, null, userPassword, nickname, email, null, null, null, null);
    }

    public static AgentDto of(AgencyDto agencyDto, AgentGroupDto agentGroupDto, String userId, String userPassword, String nickname, String email, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new AgentDto(agencyDto, agentGroupDto, userId, userPassword, nickname, email, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static AgentDto from(Agent entity) {
        return new AgentDto(
                AgencyDto.from(entity.getAgency()),
                AgentGroupDto.from(entity.getAgentGroup()),
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
    public Agent toEntity(Agency agency, AgentGroup agentGroup) {
        return Agent.of(
                agency,
                agentGroup,
                userId,
                userPassword,
                nickname,
                email
        );
    }
}
