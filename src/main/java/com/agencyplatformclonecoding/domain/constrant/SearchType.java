package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public enum SearchType {
    ID("유저 ID"),
    NICKNAME("유저 이름");

    @Getter private final String description;

    SearchType(String description) {
        this.description = description;
    }
}

