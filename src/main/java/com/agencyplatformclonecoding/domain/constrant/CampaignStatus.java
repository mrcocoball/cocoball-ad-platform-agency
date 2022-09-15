package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public class CampaignStatus {

	@Getter private final int code;
	@Getter private final String description;
	@Getter public boolean isActivated;

	public CampaignStatus (int code) {
		this.code = code;
		this.description = parseDescription(code);
	}

	public boolean isActivated() {
		return code == 100;
	}

	private String parseDescription(int code) {
		switch (code) {
			case 100:
				return "ON";
			case 200:
				return "OFF";
			default:
				return "지원하지 않는 상태값";
		}
	}
}
