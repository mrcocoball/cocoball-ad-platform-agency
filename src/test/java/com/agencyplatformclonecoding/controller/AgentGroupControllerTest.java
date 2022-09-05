package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.config.TestSecurityConfig;
import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;
import com.agencyplatformclonecoding.dto.AgentGroupWithAgentsDto;
import com.agencyplatformclonecoding.dto.AgentWithClientsDto;
import com.agencyplatformclonecoding.dto.request.AgentGroupRequest;
import com.agencyplatformclonecoding.dto.response.AgentGroupResponse;
import com.agencyplatformclonecoding.service.AgentGroupService;
import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import com.agencyplatformclonecoding.util.FormDataEncoder;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("VIEW 컨트롤러 - 에이전트 그룹 관리")
@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(AgentGroupController.class)
class AgentGroupControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean
    private AgentGroupService agentGroupService;

    @MockBean
    private PaginationService paginationService;

    public AgentGroupControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder
    ) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 그룹 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentGroupsView_thenReturnsAgentGroupsView() throws Exception {
        // Given
        given(agentGroupService.searchAgentGroups(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/agentGroups"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/index"))
                .andExpect(model().attributeExists("agentGroups"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        then(agentGroupService).should().searchAgentGroups(eq(null), eq(null), any(Pageable.class));
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 그룹 리스트 - 검색어와 함께 호출")
    @Test
    public void givenSearchKeyword_whenSearchingAgentGroupsView_thenReturnsAgentGroupsView() throws Exception {
        // Given
        SearchType searchType = SearchType.NICKNAME;
        String searchValue = "name";
        given(agentGroupService.searchAgentGroups(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                        get("/agentGroups")
                                .queryParam("searchType", searchType.name())
                                .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/index"))
                .andExpect(model().attributeExists("agentGroups"))
                .andExpect(model().attributeExists("searchTypes"));
        then(agentGroupService).should().searchAgentGroups(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 그룹 상세 정보 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingAgentGroupDetailView_thenReturnsAgentGroupDetailView() throws Exception {
        // Given
        String agentGroupId = "t-group";
        Long totalCount = 1L;
        given(agentGroupService.getAgentGroupWithAgents(agentGroupId)).willReturn(createAgentGroupWithAgentsDto());
        given(agentGroupService.getAgentGroupCount()).willReturn(totalCount);

        // When & Then
        mvc.perform(get("/agentGroups/" + agentGroupId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/detail"))
                .andExpect(model().attributeExists("agentGroup"))
                .andExpect(model().attributeExists("agents"));
    }

    @WithMockUser
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

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 그룹 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingAgentsPage_thenReturnsAgentsView() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(agentGroupService.searchAgentGroups(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                        get("/agentGroups")
                                .queryParam("page", String.valueOf(pageNumber))
                                .queryParam("size", String.valueOf(pageSize))
                                .queryParam("sort", sortName + "," + direction)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/index"))
                .andExpect(model().attributeExists("agentGroups"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(agentGroupService).should().searchAgentGroups(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 새 에이전트 그룹 생성 페이지")
    @Test
    void givenNothing_whenRequesting_thenReturnsNewAgentGroupPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/agentGroups/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/form"))
                .andExpect(model().attribute("formStatus", FormStatus.CREATE));
    }

    @WithUserDetails(value = "TestAgency", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[VIEW][POST] 새 에이전트 그룹 생성 - 정상 호출")
    @Test
    void givenNewAgentGroupInfo_whenRequesting_thenSavesNewAgentGroup() throws Exception {
        // Given
        AgentGroupRequest agentGroupRequest = AgentGroupRequest.of("테스트용 그룹");
        willDoNothing().given(agentGroupService).saveAgentGroup(any(AgentGroupDto.class));

        // When & Then
        mvc.perform(
                        post("/agentGroups/form") // post : MockMvcRequestBuilders.post
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(agentGroupRequest))
                                .with(csrf()) // csrf : SecurityMockMvcRequestPostProcessors.csrf
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/agentGroups"))
                .andExpect(redirectedUrl("/agentGroups"));
        then(agentGroupService).should().saveAgentGroup(any(AgentGroupDto.class));
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 에이전트 그룹 수정 페이지")
    @Test
    void givenNothing_whenRequesting_thenReturnsUpdatedAgentGroupPage() throws Exception {
        // Given
        String agentGroupId = "t-group";
        AgentGroupDto dto = createAgentGroupDto();
        given(agentGroupService.getAgentGroup(agentGroupId)).willReturn(dto);

        // When & Then
        mvc.perform(get("/agentGroups/" + agentGroupId + "/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("agentgroups/form"))
                .andExpect(model().attribute("agentGroup", AgentGroupResponse.from(dto)))
                .andExpect(model().attribute("formStatus", FormStatus.UPDATE));
        then(agentGroupService).should().getAgentGroup(agentGroupId);
    }

    @WithUserDetails(value = "TestAgency", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[VIEW][POST] 에이전트 그룹 수정 - 정상 호출")
    @Test
    void givenUpdatedAgentGroupInfo_whenRequesting_thenUpdatesNewAgentGroup() throws Exception {
        // Given
        String agentGroupId = "t-group";
        AgentGroupRequest agentGroupRequest = AgentGroupRequest.of("새 그룹 이름");
        willDoNothing().given(agentGroupService).updateAgentGroup(eq(agentGroupId), any(AgentGroupDto.class));

        // When & Then
        mvc.perform(
                        post("/agentGroups/" + agentGroupId + "/form")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(agentGroupRequest))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/agentGroups/"))
                .andExpect(redirectedUrl("/agentGroups/"));
        then(agentGroupService).should().updateAgentGroup(eq(agentGroupId), any(AgentGroupDto.class));
    }


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
                "t-group",
                "테스트용",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    private AgentGroupWithAgentsDto createAgentGroupWithAgentsDto() {
        return AgentGroupWithAgentsDto.of(
                createAgencyDto(),
                Set.of(),
                "t-client",
                "김테스트",
                LocalDateTime.now(),
                "김테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

}