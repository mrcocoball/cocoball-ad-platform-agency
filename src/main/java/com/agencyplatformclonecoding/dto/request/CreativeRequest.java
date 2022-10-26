package com.agencyplatformclonecoding.dto.request;

import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.CreativeDto;

public record CreativeRequest(
        Long campaignId,
        String keyword,
        Long bidingPrice,
        String description,
        String url
) {

    public static CreativeRequest of(Long campaignId, String keyword, Long bidingPrice, String description, String url) {
        return new CreativeRequest(campaignId, keyword, bidingPrice, description, url);
    }

    public CreativeDto toDto(CampaignDto campaignDto) {
        return CreativeDto.of(
                campaignDto,
                campaignDto.id(),
                keyword,
                bidingPrice,
                description,
                url
        );
    }
}
