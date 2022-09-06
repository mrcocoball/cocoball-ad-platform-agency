package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.config.TestSecurityConfig;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.*;
import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("VIEW 컨트롤러 - 에이전트 관리")
@Import(TestSecurityConfig.class)
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

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentsView_thenReturnsAgentsView() throws Exception {
        // Given
        given(agentService.searchAgents(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/agents"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agents/index"))
                .andExpect(model().attributeExists("agents"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        then(agentService).should().searchAgents(eq(null), eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 리스트 - 검색어와 함께 호출")
    @Test
    public void givenSearchKeyword_whenSearchingAgentsView_thenReturnsAgentsView() throws Exception {
        // Given
        SearchType searchType = SearchType.NICKNAME;
        String searchValue = "name";
        given(agentService.searchAgents(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                        get("/agents")
                                .queryParam("searchType", searchType.name())
                                .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agents/index"))
                .andExpect(model().attributeExists("agents"))
                .andExpect(model().attributeExists("searchTypes"));
        then(agentService).should().searchAgents(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 상세 정보 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentDetailView_thenReturnsAgentDetailView() throws Exception {
        // Given
        String agentId = "agent";
        Long totalCount = 1L;
        given(agentService.getAgentWithClients(agentId)).willReturn(createAgentWithClientsDto());
        given(agentService.getAgentCount()).willReturn(totalCount);

        // When & Then
        mvc.perform(get("/agents/" + agentId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agents/detail"))
                .andExpect(model().attributeExists("agent"))
                .andExpect(model().attributeExists("clients"))
                .andExpect(model().attribute("totalCount", totalCount));
        then(agentService).should().getAgentWithClients(agentId);
        then(agentService).should().getAgentCount();
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 상세 정보 -> 담당 광고주의 캠페인 관리 페이지로 리다이렉션")
    @Test
    void givenNothing_whenRequestingRootPage_thenRedirectsToClientsView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agents/agentId/clientId"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/manage/{clientId}/campaigns"))
                .andExpect(forwardedUrl("/manage/{clientId}/campaigns"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingAgentsPage_thenReturnsAgentsView() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(agentService.searchAgents(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                        get("/agents")
                                .queryParam("page", String.valueOf(pageNumber))
                                .queryParam("size", String.valueOf(pageSize))
                                .queryParam("sort", sortName + "," + direction)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agents/index"))
                .andExpect(model().attributeExists("agents"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(agentService).should().searchAgents(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

    //0824 추가
   	/* @DisplayName("[VIEW][POST] 에이전트 삭제 - 정상 호출")
   	@Test
   	void givenAgentIdToDelete_whenRequesting_thenDeletesAgent() throws Exception {
   		// Given
   		String agentId = "t-agent";
   		willDoNothing().given(agentService).deleteAgent(agentId);

   		// When & Then
   		mvc.perform(
   						post("/agents/" + agentId + "/delete")
   								.contentType(MediaType.APPLICATION_FORM_URLENCODED)
   								.with(csrf())
   				)
   				.andExpect(status().is3xxRedirection())
   				.andExpect(view().name("redirect::/agents"))
   				.andExpect(redirectedUrl("/agents"));
   		then(agentService).should().deleteAgenct(agentId);
   } */

    // fixture

    private AgencyDto createAgencyDto() {
        return AgencyDto.of(
                "t-agency",
                "pw",
                "테스트용"
        );
    }

    private AgentGroupDto createAgentGroupDto() {
        return AgentGroupDto.of(
                createAgencyDto(),
                1L,
                "테스트용",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    private AgentDto createAgentDto() {
        return AgentDto.of(
                createAgencyDto(),
                createAgentGroupDto(),
                "t-agent",
                "pw",
                "테스트용용",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    private AgentWithClientsDto createAgentWithClientsDto() {
        return AgentWithClientsDto.of(
                createAgencyDto(),
                createAgentGroupDto(),
                Set.of(),
                "t-client",
                "pw",
                "김테스트",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }
}
