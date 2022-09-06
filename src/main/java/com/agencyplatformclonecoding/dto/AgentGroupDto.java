package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.domain.AgentGroup;

import java.time.LocalDateTime;

public record AgentGroupDto(
		AgencyDto agencyDto,
        Long id,
        String name,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static AgentGroupDto of(AgencyDto agencyDto, String name) {
        return new AgentGroupDto(agencyDto, null, name, null, null, null, null);
    }
    public static AgentGroupDto of(AgencyDto agencyDto, Long id, String name, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new AgentGroupDto(agencyDto, id, name, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static AgentGroupDto from(AgentGroup entity) {
        return new AgentGroupDto(
                AgencyDto.from(entity.getAgency()),
				entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // dto -> Entity로 변환
    public AgentGroup toEntity(Agency agency) {
        return AgentGroup.of(
                agency,
				name
        );
    }
}
