package com.agencyplatformclonecoding.dto.request;

import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.ClientUserDto;

public record CampaignRequest(
        String name,
        Long budget
) {

    public static CampaignRequest of(String name, Long budget) {
        return new CampaignRequest(name, budget);
    }

    public CampaignDto toDto(ClientUserDto clientUserDto) {
        return CampaignDto.of(
                clientUserDto,
                name,
                budget
        );
    }

}
