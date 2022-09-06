package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.ClientUser;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ClientUserWithCampaignsDto(
        AgencyDto agencyDto,
        AgentDto agentDto,
        String userId,
        String userPassword,
        String nickname,
        String email,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        Set<CampaignDto> campaignDtos
) {

    public static ClientUserWithCampaignsDto of(AgencyDto agencyDto, AgentDto agentDto, String userId, String password, String nickname, String email, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, Set<CampaignDto> campaignDtos) {
        return new ClientUserWithCampaignsDto(agencyDto, agentDto, userId, password, nickname, email, createdAt, createdBy, modifiedAt, modifiedBy, campaignDtos);
    }

    // Entity -> dto로 변환
    public static ClientUserWithCampaignsDto from(ClientUser entity) {
        return new ClientUserWithCampaignsDto(
                AgencyDto.from(entity.getAgency()),
                AgentDto.from(entity.getAgent()),
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getNickname(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getCampaigns().stream()
                        .map(CampaignDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
