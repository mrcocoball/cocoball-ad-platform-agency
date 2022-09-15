package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.domain.constrant.CreativeStatus;

import java.time.LocalDateTime;

public record CreativeDto(
        CampaignDto campaignDto,
        Long campaignId,
        Long id,
        String keyword,
        Long bidingPrice,
        Long view,
        Long click,
        Long conversion,
        Long purchase,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        CreativeStatus status
) {

    public static CreativeDto of(CampaignDto campaignDto, Long campaignId, String keyword, Long bidingPrice) {
		CreativeStatus status = new CreativeStatus(200);
        return new CreativeDto(campaignDto, campaignId, null, keyword, bidingPrice, null, null, null, null, null, null, null, null, status);
    }

    public static CreativeDto of(CampaignDto campaignDto, Long campaignId, Long id, String keyword, Long bidingPrice, Long view, Long click, Long conversion, Long purchase, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, CreativeStatus status) {
        return new CreativeDto(campaignDto, campaignId, id, keyword, bidingPrice, view, click, conversion, purchase, createdAt, createdBy, modifiedAt, modifiedBy, status);
    }

    // Entity -> dto로 변환
    public static CreativeDto from(Creative entity) {
        return new CreativeDto(
                CampaignDto.from(entity.getCampaign()),
                entity.getCampaign().getId(),
                entity.getId(),
                entity.getKeyword(),
                entity.getBidingPrice(),
                entity.getView(),
                entity.getClick(),
                entity.getConversion(),
				entity.getPurchase(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
				entity.getStatus()
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
}
