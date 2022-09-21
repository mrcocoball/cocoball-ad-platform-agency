package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CreativeDto;

import java.io.Serializable;

public record CreativeResponse(
        Long id,
        String keyword,
        Long bidingPrice,
        Long view,
        Long click,
        Long conversion,
        Long purchase,
        Long campaignId,
        boolean activated
) implements Serializable {

    public static CreativeResponse of(Long id, String keyword, Long bidingPrice, Long view, Long click, Long conversion, Long purchase, Long campaignId, boolean activated) {
        return new CreativeResponse(id, keyword, bidingPrice, view, click, conversion, purchase, campaignId, activated);
    }

    public static CreativeResponse from(CreativeDto dto) {
        Long campaignId = dto.campaignId();
        boolean activated = dto.activated();

        return new CreativeResponse(
                dto.id(),
                dto.keyword(),
                dto.bidingPrice(),
                dto.view(),
                dto.click(),
                dto.conversion(),
                dto.purchase(),
                campaignId,
                activated
        );
    }
}
