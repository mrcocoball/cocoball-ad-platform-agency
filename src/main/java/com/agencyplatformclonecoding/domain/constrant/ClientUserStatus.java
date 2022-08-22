package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public enum ClientUserStatus {
    MAPPED("이관"),
    UNMAPPED("OLS");

    @Getter
    private final String description;

    ClientUserStatus(String description) {
        this.description = description;
    }
}

