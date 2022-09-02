package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.Creative;

import java.time.LocalDateTime;

public record CreativeDto(
        CampaignDto campaignDto,
        Long id,
        String keyword,
        Long bidingPrice,
		Long view,
		Long click,
		Long conversion,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static CreativeDto of(CampaignDto campaignDto, String keyword, Long bidingPrice, Long view, Long click, Long conversion) {
        return new CreativeDto(campaignDto, null, keyword, bidingPrice, view, click, conversion, null, null, null, null);
    }
    public static CreativeDto of(CampaignDto campaignDto, Long id, String keyword, Long bidingPrice, Long view, Long click, Long conversion, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new CreativeDto(campaignDto, id, keyword, bidingPrice, view, click, conversion, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static CreativeDto from(Creative entity) {
        return new CreativeDto(
				CampaignDto.from(entity.getCampaign()),
				entity.getId(),
                entity.getKeyword(),
                entity.getBidingPrice(),
				entity.getView(),
				entity.getClick(),
				entity.getConversion(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
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