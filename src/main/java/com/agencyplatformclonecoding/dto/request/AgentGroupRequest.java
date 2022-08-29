package com.agencyplatformclonecoding.dto.request;

import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;

public record AgentGroupRequest(
        String name
) {

    public static AgentGroupRequest of(String name) {
        return new AgentGroupRequest(name);
    }

    public AgentGroupDto toDto(AgencyDto agencyDto) {
        return AgentGroupDto.of(
                agencyDto,
                name
        );
    }

}