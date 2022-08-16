package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("구현 중")
@DisplayName("VIEW 컨트롤러 - 에이전트 관리")
@WebMvcTest(AgentController.class)
class AgentControllerTest {

    private final MockMvc mvc;

    @MockBean
    private AgentService agentService;

    @MockBean
    private PaginationService paginationService;

    public AgentControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 에이전트 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentsView_thenReturnsAgentsView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agents"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agents/index"))
                .andExpect(model().attributeExists("agents"));
    }

    //@Disabled("구현 중")
    @DisplayName("[VIEW][GET] 에이전트 상세 정보 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentDetailView_thenReturnsAgentDetailView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agents/agent1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agents/detail"))
                .andExpect(model().attributeExists("agent"));
    }

}