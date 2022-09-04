package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CampaignWithCreativesDto;
import com.agencyplatformclonecoding.dto.ClientUserWithCampaignsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record CampaignWithCreativesResponse(
        Long id,
        LocalDateTime createdAt,
        String name,
        Long budget,
        Set<CreativeResponse> creativeResponses
) implements Serializable {

    public static CampaignWithCreativesResponse of(Long id, LocalDateTime createdAt, String name, Long budget, Set<CreativeResponse> creativeResponses) {
        return new CampaignWithCreativesResponse(id, createdAt, name, budget, creativeResponses);
    }

    public static CampaignWithCreativesResponse from(CampaignWithCreativesDto dto) {
        return new CampaignWithCreativesResponse(
                dto.id(),
                dto.createdAt(),
                dto.name(),
                dto.budget(),
                dto.creativeDtos().stream()
                        .map(CreativeResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}