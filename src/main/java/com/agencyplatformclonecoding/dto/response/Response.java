package com.agencyplatformclonecoding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private String resultCode; // 결과 코드
    private T result; // 결과값

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

}
