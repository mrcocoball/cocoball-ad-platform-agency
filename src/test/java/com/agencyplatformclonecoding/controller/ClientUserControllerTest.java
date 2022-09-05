package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.SecurityConfig;
import com.agencyplatformclonecoding.config.TestSecurityConfig;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.AgentDto;
import com.agencyplatformclonecoding.dto.AgentGroupDto;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.service.ClientUserService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("VIEW 컨트롤러 - 광고주 리스트")
@Import(TestSecurityConfig.class)
@WebMvcTest(ClientUserController.class)
class ClientUserControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ClientUserService clientUserService;

    @MockBean
    private PaginationService paginationService;

    public ClientUserControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 광고주 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingClientUsersView_thenReturnsClientUsersView() throws Exception {
        // Given
		given(clientUserService.searchClientUsers(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
		given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));

        // When & Then
        mvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("clients/index"))
                .andExpect(model().attributeExists("clientUsers"))
				.andExpect(model().attributeExists("paginationBarNumbers"));
		then(clientUserService).should().searchClientUsers(eq(null), eq(null), any(Pageable.class));
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
	@DisplayName("[VIEW][GET] 광고주 리스트 - 검색어와 함께 호출")
    @Test
    public void givenSearchKeyword_whenSearchingClientUsersView_thenReturnsClientUsersView() throws Exception {
        // Given
		SearchType searchType = SearchType.NICKNAME;
		String searchValue = "name";
		given(clientUserService.searchClientUsers(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));

        // When & Then
        mvc.perform(
				get("/clients")
						.queryParam("searchType", searchType.name())
						.queryParam("searchValue", searchValue)
				)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("clients/index"))
                .andExpect(model().attributeExists("clientUsers"))
				.andExpect(model().attributeExists("searchTypes"));
		then(clientUserService).should().searchClientUsers(eq(searchType), eq(searchValue), any(Pageable.class));
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
	@DisplayName("[VIEW][GET] 광고주 상세 정보 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingClientUserDetailView_thenReturnsClientUserDetailView() throws Exception {
        // Given
		String clientId = "client";
		Long totalCount = 1L;
		given(clientUserService.getClientUser(clientId)).willReturn(createClientUserDto());
		given(clientUserService.getClientUserCount()).willReturn(totalCount);

        // When & Then
        mvc.perform(get("/clients/" + clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("clients/detail"))
                .andExpect(model().attributeExists("clientUser"))
				.andExpect(model().attribute("totalCount", totalCount));
		then(clientUserService).should().getClientUser(clientId);
		then(clientUserService).should().getClientUserCount();
    }

    @WithMockUser
    @DisplayName("[VIEW][GET] 광고주 캠페인 관리 -> 광고주 캠페인 관리 페이지로 리다이렉션")
    @Test
    public void givenClientInfo_whenRequestingClientDetailView_thenRedirectsToManageClientCampaignView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/clients/clientId/manage"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/manage/{clientId}/campaigns"))
                .andExpect(forwardedUrl("/manage/{clientId}/campaigns"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser
	@DisplayName("[VIEW][GET] 광고주 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingClientUsersPage_thenReturnsClientUsersView() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(clientUserService.searchClientUsers(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                get("/clients")
                        .queryParam("page", String.valueOf(pageNumber))
                        .queryParam("size", String.valueOf(pageSize))
                        .queryParam("sort", sortName + "," + direction)
                   )
                   .andExpect(status().isOk())
                   .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                   .andExpect(view().name("clients/index"))
                   .andExpect(model().attributeExists("clientUsers"))
                   .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(clientUserService).should().searchClientUsers(null, null, pageable);
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
                "t-group",
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

}