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
        String sBudget,
        Set<CreativeResponse> creativeResponses,
        boolean activated
) implements Serializable {

    public static CampaignWithCreativesResponse of(Long id, LocalDateTime createdAt, String name, Long budget, String sbudget, Set<CreativeResponse> creativeResponses, boolean activated) {
        return new CampaignWithCreativesResponse(id, createdAt, name, budget, sbudget, creativeResponses, activated);
    }

    public static CampaignWithCreativesResponse from(CampaignWithCreativesDto dto) {
        boolean activated = dto.activated();

        return new CampaignWithCreativesResponse(
                dto.id(),
                dto.createdAt(),
                dto.name(),
                dto.budget(),
                dto.sBudget(),
                dto.creativeDtos().stream()
                        .map(CreativeResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                activated
        );
    }
}