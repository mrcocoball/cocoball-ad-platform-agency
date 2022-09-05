package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Agency;

public record AgencyDto(
        String agencyId,
        String password,
        String agencyName
) {

    public static AgencyDto of(String agencyId, String password, String agencyName) {
        return new AgencyDto(agencyId, password, agencyName);
    }

    // Entity -> dto로 변환
    public static AgencyDto from(Agency entity) {
        return new AgencyDto(
				entity.getAgencyId(),
                entity.getPassword(),
                entity.getAgencyName()
        );
    }

    // dto -> Entity로 변환
    public Agency toEntity() {
        return Agency.of(
                agencyId,
                password,
				agencyName
        );
    }
}
