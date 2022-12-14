package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.TestSecurityConfig;
import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.ClientUserWithCampaignsDto;
import com.agencyplatformclonecoding.dto.request.CampaignRequest;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserWithCampaignsResponse;
import com.agencyplatformclonecoding.fixture.Fixture;
import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.CreativeService;
import com.agencyplatformclonecoding.service.ManageService;
import com.agencyplatformclonecoding.service.PaginationService;
import com.agencyplatformclonecoding.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("VIEW 컨트롤러 - 캠페인 관리")
@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(CampaignController.class)
class CampaignControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean private CampaignService campaignService;

    @MockBean private ManageService manageService;

    @MockBean private CreativeService creativeService;

    @MockBean private PaginationService paginationService;

    public CampaignControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder
    ) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 단일 캠페인 관리 - 소재 리스트 정상 호출")
    @Test
    public void givenClientAndCampaignInfo_whenRequestingManageDetailView_thenReturnsManageDetailView() throws Exception {
        // Given
        String clientId = "client";
        Long campaignId = 1L;
        Long totalCount = 0L;
        given(manageService.getClientUserWithCampaigns(clientId)).willReturn(Fixture.createClientUserWithCampaignsDto());
        given(campaignService.getCampaign(campaignId)).willReturn(Fixture.createCampaignDto());
        given(creativeService.getCreativeCount()).willReturn(totalCount);
        given(creativeService.searchCreatives(any(Pageable.class), eq(campaignId), eq(clientId))).willReturn(Page.empty());

        // When & Then
        mvc.perform(get("/manage/" + clientId + "/campaigns/" + campaignId + "/creatives"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/creative"))
                .andExpect(model().attributeExists("campaign"))
                .andExpect(model().attributeExists("creatives"))
				.andExpect(model().attribute("totalCount", totalCount));
    }

    // @Disabled("실제 기능은 잘 작동하나 테스트 코드 문제가 있음 (ClientUserWithCampaignsResponse 문제)")
    @WithMockUser
    @DisplayName("[VIEW][GET] 새 캠페인 생성 페이지")
    @Test
    void givenNothing_whenRequesting_thenReturnsNewCampaignPage() throws Exception {
        // Given
        String clientId = "client";
        ClientUserWithCampaignsDto clientDto = Fixture.createClientUserWithCampaignsDto();

        // When & Then
        mvc.perform(get("/manage/" + clientId + "/campaigns/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/campaign-form"))
                .andExpect(model().attribute("clientUser", ClientUserWithCampaignsResponse.from(clientDto)))
                .andExpect(model().attribute("formStatus", FormStatus.CREATE));
    }

    @WithUserDetails(value = "TestAgency", setupBefore = TestExecutionEvent.TEST_EXECUTION)
   	@DisplayName("[VIEW][POST] 새 캠페인 생성 - 정상 호출")
    @Test
    void givenNewCampaignInfo_whenRequesting_thenSavesNewCampaign() throws Exception {
        // Given
        String clientId = "client";
        CampaignRequest campaignRequest = CampaignRequest.of("테스트용 캠페인", 3000L);
        willDoNothing().given(campaignService).saveCampaign(any(CampaignDto.class));

        // When & Then
        mvc.perform(
                post("/manage/" + clientId + "/campaigns/form") // post : MockMvcRequestBuilders.post
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(campaignRequest))
                        .with(csrf()) // csrf : SecurityMockMvcRequestPostProcessors.csrf
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/{clientId}/campaigns"))
                .andExpect(redirectedUrl("/manage/client/campaigns"));
        then(campaignService).should().saveCampaign(any(CampaignDto.class));
    }

    // @Disabled("실제 기능은 잘 작동하나 테스트 코드 문제가 있음 (ClientUserWithCampaignsResponse 문제)")
    @WithMockUser
   	@DisplayName("[VIEW][GET] 캠페인 수정 페이지")
    @Test
    void givenNothing_whenRequesting_thenReturnsUpdatedCampaignPage() throws Exception {
        // Given
        String clientId = "client";
        Long campaignId = 1L;
        CampaignDto dto = Fixture.createCampaignDto();
        ClientUserWithCampaignsDto clientDto = Fixture.createClientUserWithCampaignsDto();
        given(campaignService.getCampaign(campaignId)).willReturn(dto);

        // When & Then
        mvc.perform(get("/manage/" + clientId + "/campaigns/" + campaignId + "/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/campaign-form"))
                .andExpect(model().attribute("campaign", CampaignResponse.from(dto)))
                .andExpect(model().attribute("clientUser", ClientUserWithCampaignsResponse.from(clientDto)))
                .andExpect(model().attribute("formStatus", FormStatus.UPDATE));
        then(campaignService).should().getCampaign(campaignId);
    }

    @WithUserDetails(value = "TestAgency", setupBefore = TestExecutionEvent.TEST_EXECUTION)
   	@DisplayName("[VIEW][POST] 캠페인 수정 - 정상 호출")
    @Test
    void givenUpdatedCampaignInfo_whenRequesting_thenUpdatesNewCampaign() throws Exception {
        // Given
        String clientId = "client";
        Long campaignId = 1L;
        CampaignRequest campaignRequest = CampaignRequest.of("새 캠페인", 1000L);
        willDoNothing().given(campaignService).updateCampaign(eq(campaignId), eq(clientId), any(CampaignDto.class));

        // When & Then
        mvc.perform(
                post("/manage/" + clientId + "/campaigns/" + campaignId + "/form")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(campaignRequest))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/{clientId}/campaigns"))
                .andExpect(redirectedUrl("/manage/client/campaigns"));
        then(campaignService).should().updateCampaign(eq(campaignId), eq(clientId), any(CampaignDto.class));
    }
}
