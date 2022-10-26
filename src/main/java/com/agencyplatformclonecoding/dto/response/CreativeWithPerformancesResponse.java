package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.CreativeDto;
import com.agencyplatformclonecoding.dto.CreativeWithPerformancesDto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record CreativeWithPerformancesResponse(
        Long id,
        String keyword,
        Long bidingPrice,
        String sBidingPrice,
        String description,
        String url,
        Long campaignId,
        Set<PerformanceResponse> performanceResponses,
        boolean activated
) implements Serializable {

    public static CreativeWithPerformancesResponse of(Long id, String keyword, Long bidingPrice, String sBidingPrice, String description, String url, Long campaignId, Set<PerformanceResponse> performanceResponses, boolean activated) {
        return new CreativeWithPerformancesResponse(id, keyword, bidingPrice, sBidingPrice, description, url, campaignId, performanceResponses, activated);
    }

    public static CreativeWithPerformancesResponse from(CreativeWithPerformancesDto dto) {
        Long campaignId = dto.campaignId();
        boolean activated = dto.activated();

        return new CreativeWithPerformancesResponse(
                dto.id(),
                dto.keyword(),
                dto.bidingPrice(),
                dto.sBidingPrice(),
                dto.description(),
                dto.url(),
                campaignId,
                dto.performanceDtos().stream()
                        .map(PerformanceResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                activated
        );
    }
}