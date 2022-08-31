package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.ClientUserWithCampaignsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ClientUserWithCampaignsResponse(
        String userId,
        LocalDateTime createdAt,
        String nickname,
        Set<CampaignResponse> campaignResponses
) implements Serializable {

    public static ClientUserWithCampaignsResponse of (String userId, LocalDateTime createdAt, String nickname, Set<CampaignResponse> campaignResponses) {
        return new ClientUserWithCampaignsResponse(userId, createdAt, nickname, campaignResponses);
    }

    public static ClientUserWithCampaignsResponse from(ClientUserWithCampaignsDto dto) {
        return new ClientUserWithCampaignsResponse(
                dto.userId(),
                dto.createdAt(),
                dto.nickname(),
                dto.campaignDtos().stream()
                        .map(CampaignResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
