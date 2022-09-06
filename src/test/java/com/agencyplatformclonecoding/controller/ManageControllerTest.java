package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.config.TestSecurityConfig;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("VIEW 컨트롤러 - 광고주 관리")
@Import(TestSecurityConfig.class)
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

    @WithMockUser
    @DisplayName("[VIEW][GET] 광고 관리 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingManageView_thenReturnsManageView() throws Exception {
        // Given
		given(manageService.searchClientUsers(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
		given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));

        // When & Then
        mvc.perform(get("/manage"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/index"))
                .andExpect(model().attributeExists("clientUsers"))
				.andExpect(model().attributeExists("paginationBarNumbers"));
		then(manageService).should().searchClientUsers(eq(null), eq(null), any(Pageable.class));
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
	@DisplayName("[VIEW][GET] 광고 관리 리스트 - 검색어와 함께 호출")
    @Test
    public void givenSearchKeyword_whenSearchingManageView_thenReturnsManageView() throws Exception {
        // Given
		SearchType searchType = SearchType.NICKNAME;
		String searchValue = "name";
		given(manageService.searchClientUsers(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));

        // When & Then
        mvc.perform(
				get("/manage")
						.queryParam("searchType", searchType.name())
						.queryParam("searchValue", searchValue)
				)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/index"))
                .andExpect(model().attributeExists("clientUsers"))
				.andExpect(model().attributeExists("searchTypes"));
		then(manageService).should().searchClientUsers(eq(searchType), eq(searchValue), any(Pageable.class));
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 단일 광고주 관리 - 캠페인 리스트 정상 호출")
    @Test
    public void givenClientInfo_whenRequestingManageDetailView_thenReturnsManageDetailView() throws Exception {
        // Given
		String clientId = "client";
		Long totalCount = 1L;
		given(manageService.getClientUserWithCampaigns(clientId)).willReturn(createClientUserWithCampaignsDto());
		given(manageService.getClientUserCount()).willReturn(totalCount);

        // When & Then
        mvc.perform(get("/manage/" + clientId + "/campaigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("manage/campaign"))
                .andExpect(model().attributeExists("clientUser"))
                .andExpect(model().attributeExists("campaigns"))
				.andExpect(model().attribute("totalCount", totalCount));
    }

    @WithMockUser
	@DisplayName("[VIEW][GET] 광고 관리 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingManagePage_thenReturnsManageView() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(manageService.searchClientUsers(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                get("/manage")
                        .queryParam("page", String.valueOf(pageNumber))
                        .queryParam("size", String.valueOf(pageSize))
                        .queryParam("sort", sortName + "," + direction)
                   )
                   .andExpect(status().isOk())
                   .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                   .andExpect(view().name("manage/index"))
                   .andExpect(model().attributeExists("clientUsers"))
                   .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(manageService).should().searchClientUsers(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
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

   	private ClientUserDto createClientUserDto() {
   		return ClientUserDto.of(
   				createAgencyDto(),
   				createAgentDto(),
   				"t-client",
   				"pw",
   				"테스트용",
   				"email",
   				LocalDateTime.now(),
                   "테스트",
                   LocalDateTime.now(),
                   "테스트"
           );
    }

    private ClientUserWithCampaignsDto createClientUserWithCampaignsDto() {
        return ClientUserWithCampaignsDto.of(
                createAgencyDto(),
                createAgentDto(),
                "t-client",
                "pw",
                "테스트용",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트",
                Set.of()
              );
    }

}
