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
        Long campaignId,
		Set<PerformanceResponse> performanceResponses,
        boolean activated
) implements Serializable {

    public static CreativeWithPerformancesResponse of(Long id, String keyword, Long bidingPrice, Long campaignId, Set<PerformanceResponse> performanceResponses, boolean activated) {
        return new CreativeWithPerformancesResponse(id, keyword, bidingPrice, campaignId, performanceResponses, activated);
    }

    public static CreativeWithPerformancesResponse from(CreativeWithPerformancesDto dto) {
        Long campaignId = dto.campaignId();
        boolean activated = dto.activated();

        return new CreativeWithPerformancesResponse(
                dto.id(),
                dto.keyword(),
                dto.bidingPrice(),
                campaignId,
				dto.performanceDtos().stream()
						.map(PerformanceResponse::from)
						.collect(Collectors.toCollection(LinkedHashSet::new)),
                activated
        );
    }
}