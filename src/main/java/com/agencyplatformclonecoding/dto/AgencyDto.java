package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agency;

public record AgencyDto(
        String agencyId,
        String agencyName
) {

    public static AgencyDto of(String agencyId, String agencyName) {
        return new AgencyDto(agencyId, agencyName);
    }

    // Entity -> dto로 변환
    public static AgencyDto from(Agency entity) {
        return new AgencyDto(
				entity.getAgencyId(),
                entity.getAgencyName()
        );
    }

    // dto -> Entity로 변환
    public Agency toEntity() {
        return Agency.of(
                agencyId,
				agencyName
        );
    }
}
