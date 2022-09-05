package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.ClientUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CampaignResponse(
        Long id,
        LocalDateTime createdAt,
        String name,
        Long budget,
        String clientUserName
) implements Serializable {

    public static CampaignResponse of (Long id, LocalDateTime createdAt, String name, Long budget, String clientUserName) {
        return new CampaignResponse(id, createdAt, name, budget, clientUserName);
    }

    public static CampaignResponse from (CampaignDto dto) {
        String clientUserName = dto.clientUserDto().nickname();
        if (clientUserName == null || clientUserName.isBlank()) {
            clientUserName = dto.clientUserDto().userId();
        }

        return new CampaignResponse(
                dto.id(),
                dto.createdAt(),
                dto.name(),
                dto.budget(),
                clientUserName
        );
    }
}