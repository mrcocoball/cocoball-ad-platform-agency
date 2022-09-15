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
        Set<CreativeResponse> creativeResponses,
        String statusDescription
) implements Serializable {

    public static CampaignWithCreativesResponse of(Long id, LocalDateTime createdAt, String name, Long budget, Set<CreativeResponse> creativeResponses, String statusDescription) {
        return new CampaignWithCreativesResponse(id, createdAt, name, budget, creativeResponses, statusDescription);
    }

    public static CampaignWithCreativesResponse from(CampaignWithCreativesDto dto) {
		String statusDescription = dto.status().getDescription();

        return new CampaignWithCreativesResponse(
                dto.id(),
                dto.createdAt(),
                dto.name(),
                dto.budget(),
                dto.creativeDtos().stream()
                        .map(CreativeResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
				statusDescription
        );
    }
}