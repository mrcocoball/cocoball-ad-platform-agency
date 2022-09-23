package com.agencyplatformclonecoding.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    OK(HttpStatus.OK, "ok"),

    AGENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Agent not founded"),
    AGENT_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "Agent Group not founded"),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Client not founded"),
    CAMPAIGN_NOT_FOUND(HttpStatus.NOT_FOUND, "Campaign not founded"),
    CREATIVE_NOT_FOUND(HttpStatus.NOT_FOUND, "Campaign not founded"),
    PERFORMANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Performance not founded"),
    AGENT_EXISTS(HttpStatus.CONFLICT, "Agent exists"),
    CLIENT_EXISTS(HttpStatus.CONFLICT, "Client exists"),
    INVALID_RELATION(HttpStatus.CONFLICT, "Client, Campaign, Creative does not match with each other"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private HttpStatus status;
    private String message;

}
