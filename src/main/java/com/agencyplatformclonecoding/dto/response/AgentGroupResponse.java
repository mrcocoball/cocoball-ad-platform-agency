package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AgentGroupResponse(
        Long id,
        LocalDateTime createdAt,
        String name
) implements Serializable {

    public static AgentGroupResponse of(Long id, LocalDateTime createdAt, String name) {
        return new AgentGroupResponse(id, createdAt, name);
    }

    public static AgentGroupResponse from(AgentGroupDto dto) {
        return new AgentGroupResponse(
                dto.id(),
                dto.createdAt(),
                dto.name()
        );
    }
}

