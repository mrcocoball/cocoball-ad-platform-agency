package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public enum ReportType {
    PERFORMANCE("실적 보고서"),
    CREATIVE("소재 보고서"),
    CAMPAIGN("캠페인 보고서"),
    CLIENT("광고주 보고서");

    @Getter
    private final String description;

    ReportType(String description) {
        this.description = description;
    }
}
