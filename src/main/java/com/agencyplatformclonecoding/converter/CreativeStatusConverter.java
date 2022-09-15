package com.agencyplatformclonecoding.converter;

import com.agencyplatformclonecoding.domain.constrant.CreativeStatus;

import javax.persistence.AttributeConverter;

public class CreativeStatusConverter implements AttributeConverter<CreativeStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(CreativeStatus attribute) {
		return attribute.getCode();
	}

	@Override
    public CreativeStatus convertToEntityAttribute(Integer data) {
        return data != null ? new CreativeStatus(data) : null;
    }
}
