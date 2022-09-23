package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.PerformanceDto;

import java.io.Serializable;
import java.time.LocalDate;

public record PerformanceResponse(
        Long id,
        Long view,
        Long click,
        Long conversion,
        Long purchase,
		LocalDate createdAt,
		Long creativeId
) implements Serializable {

    public static PerformanceResponse of(Long id, Long view, Long click, Long conversion, Long purchase, LocalDate createdAt, Long creativeId) {
        return new PerformanceResponse(id, view, click, conversion, purchase, createdAt, creativeId);
    }

    public static PerformanceResponse from(PerformanceDto dto) {
        Long creativeId = dto.creativeId();

        return new PerformanceResponse(
                dto.id(),
                dto.view(),
                dto.click(),
                dto.conversion(),
                dto.purchase(),
				dto.createdAt(),
                creativeId
        );
    }
}