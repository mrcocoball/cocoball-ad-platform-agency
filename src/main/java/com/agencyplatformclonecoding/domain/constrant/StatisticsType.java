package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public enum StatisticsType {
    BEFORE_WEEK("최근 7일"),
    BEFORE_MONTH("최근 30일");

    @Getter private final String description;

    StatisticsType(String description) {
        this.description = description;
    }
}
