package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.domain.Performance;

import java.time.LocalDate;

public record PerformanceDto(
        CreativeDto creativeDto,
		Long creativeId,
        Long id,
        Long view,
        Long click,
        Long conversion,
        Long purchase,
        LocalDate createdAt
) {

    public static PerformanceDto of(CreativeDto creativeDto, Long creativeId, Long view, Long click, Long conversion, Long purchase) {
        return new PerformanceDto(creativeDto, creativeId, null, view, click, conversion, purchase, null);
    }

    public static PerformanceDto of(CreativeDto creativeDto, Long creativeId, Long id, Long view, Long click, Long conversion, Long purchase, LocalDate createdAt) {
        return new PerformanceDto(creativeDto, creativeId, id, view, click, conversion, purchase, createdAt);
    }

    // Entity -> dto로 변환
    public static PerformanceDto from(Performance entity) {
        return new PerformanceDto(
                CreativeDto.from(entity.getCreative()),
                entity.getCreative().getId(),
                entity.getId(),
                entity.getView(),
                entity.getClick(),
                entity.getConversion(),
				entity.getPurchase(),
                entity.getCreatedAt()
        );
    }

    // dto -> Entity로 변환
    public Performance toEntity(Creative creative) {
        return Performance.of(
                creative,
				view,
				click,
				conversion,
				purchase
        );
    }
}