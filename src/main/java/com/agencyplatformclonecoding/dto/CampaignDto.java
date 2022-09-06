package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;

import java.time.LocalDateTime;

public record CampaignDto(
        ClientUserDto clientUserDto,
        Long id,
        String name,
        Long budget,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static CampaignDto of(ClientUserDto clientUserDto, String name, Long budget) {
        return new CampaignDto(clientUserDto, null, name, budget, null, null, null, null);
    }

    public static CampaignDto of(Long id) {
        return new CampaignDto(null, id, null, null, null, null, null, null);
    }

    public static CampaignDto of(ClientUserDto clientUserDto, Long id, String name, Long budget, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new CampaignDto(clientUserDto, id, name, budget, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static CampaignDto from(Campaign entity) {
        return new CampaignDto(
                ClientUserDto.from(entity.getClientUser()),
                entity.getId(),
                entity.getName(),
                entity.getBudget(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // dto -> Entity로 변환
    public Campaign toEntity(ClientUser clientUser) {
        return Campaign.of(
                clientUser,
                name,
                budget
        );
    }
}
