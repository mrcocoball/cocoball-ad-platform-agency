package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.service.AgentGroupService;
import com.agencyplatformclonecoding.service.AgentService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("VIEW 컨트롤러 - 에이전트 그룹 관리")
@Import(SecurityConfig.class)
@WebMvcTest(AgentGroupController.class)
class AgentGroupControllerTest {

    private final MockMvc mvc;

    @MockBean
    private AgentGroupService agentGroupService;

    @MockBean
    private PaginationService paginationService;

    public AgentGroupControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    @DisplayName("[VIEW][GET] 에이전트 그룹 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentGroupsView_thenReturnsAgentGroupsView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agentGroups"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/index"))
                .andExpect(model().attributeExists("agentGroups"));
    }

    @DisplayName("[VIEW][GET] 에이전트 그룹 상세 정보 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentGroupDetailView_thenReturnsAgentGroupDetailView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agentGroups/mkt1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/detail"))
                .andExpect(model().attributeExists("agentGroup"))
                .andExpect(model().attributeExists("agents"));
    }

    @DisplayName("[VIEW][GET] 에이전트 그룹 상세 정보 -> 해당 에이전트 관리 페이지로 리다이렉션")
    @Test
    void givenNothing_whenRequestingAgentView_thenRedirectsToAgentView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agentGroups/agentGroupId/agnetId"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/agents/{agentId}"))
                .andExpect(forwardedUrl("/agents/{agentId}"))
                .andDo(MockMvcResultHandlers.print());
    }

}