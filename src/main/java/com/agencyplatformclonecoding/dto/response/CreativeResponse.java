package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CreativeDto;

public record CreativeResponse(
        Long id,
        String keyword,
        Long bidingPrice,
        Long view,
        Long click,
        Long conversion,
        Long purchase,
        Long campaignId,
        String statusDescription
) {

    public static CreativeResponse of(Long id, String keyword, Long bidingPrice, Long view, Long click, Long conversion, Long purchase, Long campaignId, String statusDescription) {
        return new CreativeResponse(id, keyword, bidingPrice, view, click, conversion, purchase, campaignId, statusDescription);
    }

    public static CreativeResponse from(CreativeDto dto) {
        Long campaignId = dto.campaignId();
        String statusDescription = dto.status().getDescription();

        return new CreativeResponse(
                dto.id(),
                dto.keyword(),
                dto.bidingPrice(),
                dto.view(),
                dto.click(),
                dto.conversion(),
                dto.purchase(),
                campaignId,
                statusDescription
        );
    }
}
