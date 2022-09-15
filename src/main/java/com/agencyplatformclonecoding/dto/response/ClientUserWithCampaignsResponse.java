package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.ClientUserWithCampaignsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ClientUserWithCampaignsResponse(
        String userId,
        String categoryName,
        LocalDateTime createdAt,
        String nickname,
        Set<CampaignResponse> campaignResponses
) implements Serializable {

    public static ClientUserWithCampaignsResponse of(String userId, String categoryName, LocalDateTime createdAt, String nickname, Set<CampaignResponse> campaignResponses) {
        return new ClientUserWithCampaignsResponse(userId, categoryName, createdAt, nickname, campaignResponses);
    }

    public static ClientUserWithCampaignsResponse from(ClientUserWithCampaignsDto dto) {
        String categoryName = dto.categoryDto().name();

        return new ClientUserWithCampaignsResponse(
                dto.userId(),
                categoryName,
                dto.createdAt(),
                dto.nickname(),
                dto.campaignDtos().stream()
                        .map(CampaignResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
