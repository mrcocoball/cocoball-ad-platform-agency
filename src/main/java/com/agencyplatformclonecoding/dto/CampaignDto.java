package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.constrant.CampaignStatus;

import java.time.LocalDateTime;

public record CampaignDto(
        ClientUserDto clientUserDto,
        Long id,
        String name,
        Long budget,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        CampaignStatus status
) {

    public static CampaignDto of(ClientUserDto clientUserDto, String name, Long budget) {
        CampaignStatus status = new CampaignStatus(200);
        return new CampaignDto(clientUserDto, null, name, budget, null, null, null, null, status);
    }

    public static CampaignDto of(Long id) {
        CampaignStatus status = new CampaignStatus(200);
        return new CampaignDto(null, id, null, null, null, null, null, null, status);
    }

    public static CampaignDto of(ClientUserDto clientUserDto, Long id, String name, Long budget, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, CampaignStatus status) {
        return new CampaignDto(clientUserDto, id, name, budget, createdAt, createdBy, modifiedAt, modifiedBy, status);
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
                entity.getModifiedBy(),
				entity.getStatus()
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
