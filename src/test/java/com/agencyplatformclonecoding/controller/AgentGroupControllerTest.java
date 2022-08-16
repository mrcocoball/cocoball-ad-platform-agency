package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.AgentGroupService;
import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@Disabled("구현 중")
@DisplayName("VIEW 컨트롤러 - 에이전트 그룹 관리")
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

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 에이전트 그룹 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentGroupsView_thenReturnsAgentGroupsView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agentGroups"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentGroups/index"))
                .andExpect(model().attributeExists("agentGroups"));
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 에이전트 그룹 상세 정보 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentGroupDetailView_thenReturnsAgentGroupDetailView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agentGroups/mkt1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentGroups/detail"))
                .andExpect(model().attributeExists("agentGroup"));
    }

}