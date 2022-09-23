package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Creative;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record CreativeWithPerformancesDto(
        CampaignDto campaignDto,
        Long campaignId,
        Long id,
        String keyword,
        Long bidingPrice,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        Set<PerformanceDto> performanceDtos,
        boolean activated
) {

    public static CreativeWithPerformancesDto of(CampaignDto campaignDto, Long campaignId, Long id, String keyword, Long bidingPrice, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, Set<PerformanceDto> performanceDtos, boolean activated) {
        return new CreativeWithPerformancesDto(campaignDto, campaignId, id, keyword, bidingPrice, createdAt, createdBy, modifiedAt, modifiedBy, performanceDtos, activated);
    }

    // Entity -> dto로 변환
    public static CreativeWithPerformancesDto from(Creative entity) {
        return new CreativeWithPerformancesDto(
                CampaignDto.from(entity.getCampaign()),
                entity.getCampaign().getId(),
                entity.getId(),
                entity.getKeyword(),
                entity.getBidingPrice(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getPerformances().stream()
                        .map(PerformanceDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
				entity.isActivated()
        );
    }
}
