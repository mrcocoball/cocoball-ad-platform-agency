package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.response.ClientUserResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserWithCampaignsResponse;
import com.agencyplatformclonecoding.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("manage")
@Controller
public class ManageController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final PaginationService paginationService;

    @GetMapping()
    public String manage(
				@RequestParam(required = false) SearchType searchType,
				@RequestParam(required = false) String searchValue,
				@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
				ModelMap map
		) {
		Page<ClientUserResponse> clientUsers = manageService.searchClientUsers(searchType, searchValue, pageable)
    				.map(ClientUserResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), clientUsers.getTotalPages());
        map.addAttribute("clientUsers", clientUsers);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "manage/index";
    }

    @GetMapping("/{clientId}/campaigns")
    public String campaigns(@PathVariable String clientId, ModelMap map) {
		ClientUserWithCampaignsResponse clientUserWithCampaigns = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));

		map.addAttribute("clientUser", clientUserWithCampaigns);
        map.addAttribute("campaigns", clientUserWithCampaigns.campaignResponses());
        map.addAttribute("totalCount", manageService.getClientUserCount());

        return "manage/campaign";
    }

}
