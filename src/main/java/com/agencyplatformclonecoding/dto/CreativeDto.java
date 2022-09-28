package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.Creative;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public record CreativeDto(
        CampaignDto campaignDto,
        Long campaignId,
        Long id,
        String keyword,
        Long bidingPrice,
        String sBidingPrice,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        boolean activated
) {

    public static CreativeDto of(CampaignDto campaignDto, Long campaignId, String keyword, Long bidingPrice) {

        String sBidingPrice = formatToString(bidingPrice);

        return new CreativeDto(campaignDto, campaignId, null, keyword, bidingPrice, sBidingPrice, null, null, null, null, false);
    }

    public static CreativeDto of(CampaignDto campaignDto, Long campaignId, Long id, String keyword, Long bidingPrice, String sBidingPrice, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, boolean activated) {
        return new CreativeDto(campaignDto, campaignId, id, keyword, bidingPrice, sBidingPrice, createdAt, createdBy, modifiedAt, modifiedBy, activated);
    }

    // Entity -> dto로 변환
    public static CreativeDto from(Creative entity) {

        String sBidingPrice = formatToString(entity.getBidingPrice());

        return new CreativeDto(
                CampaignDto.from(entity.getCampaign()),
                entity.getCampaign().getId(),
                entity.getId(),
                entity.getKeyword(),
                entity.getBidingPrice(),
                sBidingPrice,
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.isActivated()
        );
    }

    // dto -> Entity로 변환
    public Creative toEntity(Campaign campaign) {
        return Creative.of(
                campaign,
                keyword,
                bidingPrice
        );
    }

    public static String formatToString(Long amount) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(amount);
    }
}
