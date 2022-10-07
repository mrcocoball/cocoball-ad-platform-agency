package com.agencyplatformclonecoding.dto.request;

import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.CreativeDto;

public record CreativeRequest(
        Long campaignId,
        String keyword,
        Long bidingPrice
) {

    public static CreativeRequest of(Long campaignId, String keyword, Long bidingPrice) {
        return new CreativeRequest(campaignId, keyword, bidingPrice);
    }

    public CreativeDto toDto(CampaignDto campaignDto) {
        return CreativeDto.of(
                campaignDto,
                campaignDto.id(),
                keyword,
                bidingPrice
        );
    }
}
