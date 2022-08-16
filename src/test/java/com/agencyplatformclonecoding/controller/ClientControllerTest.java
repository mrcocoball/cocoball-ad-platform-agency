package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.ClientService;
import com.agencyplatformclonecoding.service.PaginationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("구현 중")
@DisplayName("VIEW 컨트롤러 - 광고주 리스트")
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private PaginationService paginationService;

    public ClientControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    @DisplayName("[VIEW][GET] 광고주 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingClientsView_thenReturnsClientsView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("clients/index"))
                .andExpect(model().attributeExists("clients"));
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 광고주 상세 정보 -> 광고주 캠페인 관리 페이지로 리다이렉션")
    @Test
    public void givenClientInfo_whenRequestingClientDetailView_thenRedirectsToManageClientCampaignView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/clients/c1"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/manage/c1"))
                .andExpect(forwardedUrl("/manage/c1"))
                .andDo(MockMvcResultHandlers.print());
    }

}