package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.CreativeService;
import com.agencyplatformclonecoding.service.PaginationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("VIEW 컨트롤러 - 소재 관리")
@Import(SecurityConfig.class)
@WebMvcTest(CreativeController.class)
class CreativeControllerTest {

    private final MockMvc mvc;

    @MockBean
    private CreativeService creativeService;

    @MockBean
    private PaginationService paginationService;

    public CreativeControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    @DisplayName("[VIEW][GET] 소재 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingCreativesView_thenReturnsCreativesView() throws Exception {
        // Given

        // When & Then
        Long campaignId = 1L;
        mvc.perform(get("/manage/clientId/campaigns/"+ campaignId +"/creatives"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/creative"))
                .andExpect(model().attributeExists("creatives"));
    }

}
