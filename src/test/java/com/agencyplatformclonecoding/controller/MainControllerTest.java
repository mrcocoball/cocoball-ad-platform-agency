package com.agencyplatformclonecoding.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("구현 중")
@DisplayName("VIEW 컨트롤러 - 리다이렉션")
@WebMvcTest(MainController.class)
class MainControllerTest {

    private final MockMvc mvc;

    public MainControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 루트 페이지 -> 광고주 리스트 페이지로 리다이렉션")
    @Test
    void givenNothing_whenRequestingRootPage_thenRedirectsToClientsView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/clients"))
                .andExpect(forwardedUrl("/clients"))
                .andDo(MockMvcResultHandlers.print());
    }

}