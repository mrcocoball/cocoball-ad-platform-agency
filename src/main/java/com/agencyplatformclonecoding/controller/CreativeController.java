package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.dto.request.CampaignRequest;
import com.agencyplatformclonecoding.dto.request.CreativeRequest;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
import com.agencyplatformclonecoding.dto.response.CampaignWithCreativesResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserWithCampaignsResponse;
import com.agencyplatformclonecoding.dto.response.CreativeResponse;
import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.CreativeService;
import com.agencyplatformclonecoding.service.ManageService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/manage/{clientId}/campaigns/{campaignId}/creatives")
@Controller
public class CreativeController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;

    @GetMapping("/form")
    public String creativeForm(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            ModelMap map
    ) {
        CampaignWithCreativesResponse campaign = CampaignWithCreativesResponse.from(campaignService.getCampaignWithCreatives(campaignId));
        ClientUserWithCampaignsResponse clientUser = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));

        map.addAttribute("clientUser", clientUser);
        map.addAttribute("campaign", campaign);
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "manage/creative-form";
    }

    @PostMapping("/form")
    public String createNewCreative(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            CreativeRequest creativeRequest
    ) {
        creativeService.saveCreative(creativeRequest.toDto(CampaignDto.of(campaignId)));

        return "redirect:/manage/{clientId}/campaigns/{campaignId}/creatives";
    }

    @GetMapping("/{creativeId}/form")
    public String updateCreativeForm(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            @PathVariable Long creativeId,
            ModelMap map
    ) {
        CreativeResponse creative = CreativeResponse.from(creativeService.getCreative(creativeId));
        CampaignWithCreativesResponse campaign = CampaignWithCreativesResponse.from(campaignService.getCampaignWithCreatives(campaignId));
        ClientUserWithCampaignsResponse clientUser = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));

        map.addAttribute("clientUser", clientUser);
        map.addAttribute("campaign", campaign);
        map.addAttribute("creative", creative);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "manage/creative-form";
    }

    @PostMapping("/{creativeId}/form")
    public String updateCreative(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            @PathVariable Long creativeId,
            CreativeRequest creativeRequest
    ) {
        creativeService.updateCreative(creativeId, creativeRequest.toDto(CampaignDto.of(campaignId)));  // TODO : 추후 에이전시 인증 기능 부여

        return "redirect:/manage/{clientId}/campaigns/{campaignId}/creatives";
    }

    @PostMapping("/{creativeId}/delete")
    public String deleteCampaignId(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            @PathVariable Long creativeId
    ) {
        creativeService.deleteCreative(creativeId);

        return "redirect:/manage/{clientId}/campaigns/{campaignId}/creatives";
    }
}