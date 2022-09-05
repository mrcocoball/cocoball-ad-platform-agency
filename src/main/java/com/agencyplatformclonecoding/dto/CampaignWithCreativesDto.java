package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Campaign;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record CampaignWithCreativesDto(
        ClientUserDto clientUserDto,
        Long id,
        String name,
        Long budget,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
		Set<CreativeDto> creativeDtos
) {

    public static CampaignWithCreativesDto of(ClientUserDto clientUserDto, Long id, String name, Long budget, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, Set<CreativeDto> creativeDtos) {
        return new CampaignWithCreativesDto(clientUserDto, id, name, budget, createdAt, createdBy, modifiedAt, modifiedBy, creativeDtos);
    }

    // Entity -> dto로 변환
    public static CampaignWithCreativesDto from(Campaign entity) {
        return new CampaignWithCreativesDto(
                ClientUserDto.from(entity.getClientUser()),
				entity.getId(),
                entity.getName(),
                entity.getBudget(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
				entity.getCreatives().stream()
								.map(CreativeDto::from)
								.collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}