package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.CampaignWithCreativesDto;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.dto.ClientUserWithCampaignsDto;
import com.agencyplatformclonecoding.dto.request.AgentGroupRequest;
import com.agencyplatformclonecoding.dto.request.CampaignRequest;
import com.agencyplatformclonecoding.dto.response.*;
import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.ManageService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/manage/{clientId}/campaigns")
@Controller
public class CampaignController {

    private final ManageService manageService;
    private final CampaignService campaignService;

    @GetMapping("/form")
    public String campaignForm(
            @PathVariable("clientId") String clientId,
            ModelMap map
    ) {
        ClientUserWithCampaignsResponse clientUser = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));

        map.addAttribute("clientUser", clientUser);
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "manage/campaign-form";
    }

    @PostMapping("/form")
    public String createNewCampaign(
            @PathVariable("clientId") String clientId,
            CampaignRequest campaignRequest
    ) {
        campaignService.saveCampaign(campaignRequest.toDto(ClientUserDto.of(clientId)));

        return "redirect:/manage/{clientId}/campaigns";
    }

    @GetMapping("/{campaignId}/form")
    public String updateCampaignForm(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId,
            ModelMap map
    ) {
        CampaignResponse campaign = CampaignResponse.from(campaignService.getCampaign(campaignId));
        ClientUserWithCampaignsResponse clientUser = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));

        map.addAttribute("clientUser", clientUser);
        map.addAttribute("campaign", campaign);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "manage/campaign-form";
    }

    @PostMapping("/{campaignId}/form")
    public String updateCampaign(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId,
            CampaignRequest campaignRequest
    ) {
        campaignService.updateCampaign(campaignId, campaignRequest.toDto(ClientUserDto.of(clientId)));  // TODO : 추후 에이전시 인증 기능 부여

        return "redirect:/manage/{clientId}/campaigns";
    }

    @PostMapping("/{campaignId}/delete")
    public String deleteCampaign(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId
    ) {
        campaignService.deleteCampaign(campaignId);

        return "redirect:/manage/{clientId}/campaigns";
    }

    @GetMapping("/{campaignId}/creatives")
    public String creatives(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId,
            ModelMap map
    ) {
        CampaignWithCreativesResponse campaignWithCreatives = CampaignWithCreativesResponse.from(campaignService.getCampaignWithCreatives(campaignId));
        ClientUserWithCampaignsResponse clientUserWithCampaignsResponse = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));

        map.addAttribute("clientUser", clientUserWithCampaignsResponse);
        map.addAttribute("campaign", campaignWithCreatives);
        map.addAttribute("creatives", campaignWithCreatives.creativeResponses());
        map.addAttribute("totalCount", campaignService.getCampaignCount());

        return "manage/creative";
    }

}

