package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CreativeDto;

public record CreativeResponse(
        Long id,
        String keyword,
        Long bidingPrice,
        Long view,
        Long click,
        Long conversion,
        Long campaignId
) {

    public static CreativeResponse of(Long id, String keyword, Long bidingPrice, Long view, Long click, Long conversion, Long campaignId) {
        return new CreativeResponse(id, keyword, bidingPrice, view, click, conversion, campaignId);
    }

    public static CreativeResponse from(CreativeDto dto) {
        Long campaignId = dto.campaignId();

        return new CreativeResponse(
                dto.id(),
                dto.keyword(),
                dto.bidingPrice(),
                dto.view(),
                dto.click(),
                dto.conversion(),
                campaignId
        );
    }
}
