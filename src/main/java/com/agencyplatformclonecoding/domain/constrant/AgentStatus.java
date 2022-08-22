package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public enum AgentStatus {
    ACTIVATE("활성화"),
    DISABLED("탈퇴됨");

    @Getter
    private final String description;

    AgentStatus(String description) {
        this.description = description;
    }
}
