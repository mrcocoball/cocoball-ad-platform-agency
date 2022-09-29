package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.domain.constrant.StatisticsType;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.dto.request.CampaignRequest;
import com.agencyplatformclonecoding.dto.request.CreativeRequest;
import com.agencyplatformclonecoding.dto.response.*;
import com.agencyplatformclonecoding.service.*;
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
@RequestMapping(value = "/manage/{clientId}/campaigns/{campaignId}/creatives")
@Controller
public class CreativeController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final PerformanceService performanceService;
    private final StatisticsService statisticsService;
    private final PaginationService paginationService;

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
        creativeService.validateClientAndCampaign(campaignId, clientId);
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
        creativeService.validateClientAndCampaignAndCreative(creativeId, campaignId, clientId);
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
        creativeService.validateClientAndCampaignAndCreative(creativeId, campaignId, clientId);
        creativeService.updateCreative(creativeId, campaignId, clientId, creativeRequest.toDto(CampaignDto.of(campaignId)));  // TODO : 추후 에이전시 인증 기능 부여

        return "redirect:/manage/{clientId}/campaigns/{campaignId}/creatives";
    }

    @PostMapping("/{creativeId}/delete")
    public String deleteCampaignId(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            @PathVariable Long creativeId
    ) {
        creativeService.validateClientAndCampaignAndCreative(creativeId, campaignId, clientId);
        creativeService.deleteCreative(creativeId, campaignId, clientId);

        return "redirect:/manage/{clientId}/campaigns/{campaignId}/creatives";
    }

    @PostMapping("/{creativeId}/activate")
    public String activateToggle(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            @PathVariable Long creativeId
    ) {
        creativeService.toggleCreativeActivate(creativeId, campaignId, clientId);

        return "redirect:/manage/{clientId}/campaigns/{campaignId}/creatives";
    }

    @GetMapping("/{creativeId}/performances")
    public String performances(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId,
            @PathVariable Long creativeId,
            @RequestParam(required = false) StatisticsType statisticsType,
            @PageableDefault(size = 7, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        CreativeWithPerformancesResponse creativeWithPerformancesResponse = CreativeWithPerformancesResponse.from(creativeService.getCreativeWithPerformances(creativeId));
        CampaignWithCreativesResponse campaignWithCreativesResponse = CampaignWithCreativesResponse.from(campaignService.getCampaignWithCreatives(campaignId));
        ClientUserWithCampaignsResponse clientUserWithCampaignsResponse = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));
        Page<PerformanceResponse> performances = performanceService.searchPerformances(pageable, statisticsType, creativeId, campaignId, clientId).map(PerformanceResponse::from);
        PerformanceStatisticsResponse statistic = PerformanceStatisticsResponse.from(statisticsService.totalPerformanceStatistics(statisticsType, creativeId));
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), performances.getTotalPages());

        map.addAttribute("clientUser", clientUserWithCampaignsResponse);
        map.addAttribute("campaign", campaignWithCreativesResponse);
        map.addAttribute("creative", creativeWithPerformancesResponse);
        map.addAttribute("performances", performances);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("statistic", statistic);
        map.addAttribute("totalCount", performanceService.getPerformanceCount());

        return "manage/performance";
    }
}