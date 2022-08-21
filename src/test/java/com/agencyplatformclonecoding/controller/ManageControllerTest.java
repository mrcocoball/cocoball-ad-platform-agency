package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.CreativeService;
import com.agencyplatformclonecoding.service.ManageService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("VIEW 컨트롤러 - 광고주 관리")
@Import(SecurityConfig.class)
@WebMvcTest(ManageController.class)
class ManageControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ManageService manageService;

    @MockBean
    private PaginationService paginationService;

    @MockBean
    private CampaignService campaignService;

    @MockBean
    private CreativeService creativeService;

    public ManageControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 전체 광고주 관리 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingManageView_thenReturnsManageView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/manage"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/index"))
                .andExpect(model().attributeExists("manage"));
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 특정 광고주 정보 조회 - 정상 호출")
    @Test
    public void givenClientInfo_whenRequestingManageDetailView_thenReturnsManageDetailView() throws Exception {
        // Given : 추후 구현

        // When & Then
        mvc.perform(get("/manage/clientId/campaigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/client"))
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("campaigns"));
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 특정 광고주의 특정 캠페인 정보 조회 - 정상 호출")
    @Test
    public void givenClientAndCampaignInfo_whenRequestingManageDetailView_thenReturnsManageDetailView() throws Exception {
        // Given : 추후 구현

        // When & Then
        mvc.perform(get("/manage/clientId/campaigns/campaignId"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/campaign"))
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("campaignId"))
                .andExpect(model().attributeExists("creatives"));
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 특정 광고주의 특정 캠페인의 단일 소재 조회 - 정상 호출")
    @Test
    public void givenClientAndCampaignAndCreativeInfo_whenRequestingManageDetailView_thenReturnsManageDetailView() throws Exception {
        // Given : 추후 구현

        // When & Then
        mvc.perform(get("/manage/clientId/campaigns/campaignId/creatives/creativeId"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/creative"))
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("campaignId"))
                .andExpect(model().attributeExists("creativeId"));
    }

}