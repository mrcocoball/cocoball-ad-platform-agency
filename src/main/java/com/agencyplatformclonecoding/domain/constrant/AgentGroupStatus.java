package com.agencyplatformclonecoding.domain.constrant;

import lombok.Getter;

public enum AgentGroupStatus {
    ACTIVATE("활성화"),
    DELETED("삭제됨");

    @Getter
    private final String description;

    AgentGroupStatus(String description) {
        this.description = description;
    }
}
