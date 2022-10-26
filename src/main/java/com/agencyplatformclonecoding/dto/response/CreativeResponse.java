package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CreativeDto;

import java.io.Serializable;

public record CreativeResponse(
        Long id,
        String keyword,
        Long bidingPrice,
        String sBidingPrice,
        String description,
        String url,
        Long campaignId,
        boolean activated
) implements Serializable {

    public static CreativeResponse of(Long id, String keyword, Long bidingPrice, String sBidingPrice, String description, String url, Long campaignId, boolean activated) {
        return new CreativeResponse(id, keyword, bidingPrice, sBidingPrice, description, url, campaignId, activated);
    }

    public static CreativeResponse from(CreativeDto dto) {
        Long campaignId = dto.campaignId();
        boolean activated = dto.activated();

        return new CreativeResponse(
                dto.id(),
                dto.keyword(),
                dto.bidingPrice(),
                dto.sBidingPrice(),
                dto.description(),
                dto.url(),
                campaignId,
                activated
        );
    }
}
