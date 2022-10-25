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

    AGENT_NOT_FOUND(HttpStatus.NOT_FOUND, "에이전트를 찾을 수 없습니다."),
    AGENT_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "에이전트 그룹을 찾을 수 없습니다."),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "광고주를 찾을 수 없습니다."),
    CAMPAIGN_NOT_FOUND(HttpStatus.NOT_FOUND, "캠페인을 찾을 수 없습니다."),
    CREATIVE_NOT_FOUND(HttpStatus.NOT_FOUND, "소재를 찾을 수 없습니다."),
    PERFORMANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "실적을 찾을 수 없습니다."),
    AGENT_EXISTS(HttpStatus.CONFLICT, "그룹 내 에이전트가 존재하여 삭제 할 수 없습니다."),
    CLIENT_EXISTS(HttpStatus.CONFLICT, "매핑된 광고주가 존재하여 삭제할 수 없습니다."),
    INVALID_RELATION(HttpStatus.CONFLICT, "잘못된 접근입니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private HttpStatus status;
    private String message;

}
