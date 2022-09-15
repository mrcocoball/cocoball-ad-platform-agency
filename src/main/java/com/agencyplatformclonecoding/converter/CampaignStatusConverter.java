package com.agencyplatformclonecoding.converter;

import com.agencyplatformclonecoding.domain.constrant.CampaignStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CampaignStatusConverter implements AttributeConverter<CampaignStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(CampaignStatus attribute) {
        return attribute.getCode();
    }

	@Override
    public CampaignStatus convertToEntityAttribute(Integer data) {
        return data != null ? new CampaignStatus(data) : null;
    }
}
