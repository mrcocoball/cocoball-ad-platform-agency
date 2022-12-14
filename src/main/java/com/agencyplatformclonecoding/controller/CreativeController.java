package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.domain.constrant.ReportType;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.dto.request.CreativeRequest;
import com.agencyplatformclonecoding.dto.response.*;
import com.agencyplatformclonecoding.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
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
    private final DashboardService dashboardService;
    private final ReportService reportService;
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
        creativeService.updateCreative(creativeId, campaignId, clientId, creativeRequest.toDto(CampaignDto.of(campaignId)));  // TODO : ?????? ???????????? ?????? ?????? ??????

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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            @PageableDefault(size = 7, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map,
            Model model
    ) {
        CreativeWithPerformancesResponse creativeWithPerformancesResponse = CreativeWithPerformancesResponse.from(creativeService.getCreativeWithPerformances(creativeId));
        CampaignWithCreativesResponse campaignWithCreativesResponse = CampaignWithCreativesResponse.from(campaignService.getCampaignWithCreatives(campaignId));
        ClientUserWithCampaignsResponse clientUserWithCampaignsResponse = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));
        Page<PerformanceResponse> performances = performanceService.searchPerformances(pageable, startDate, lastDate, creativeId, campaignId, clientId).map(PerformanceResponse::from);
        List<PerformanceStatisticsDto> statistics = statisticsService.getTotalPerformanceStatistics(startDate, lastDate, creativeId);
        List<DashboardStatisticsDto> chart = dashboardService.setPerformanceChart(startDate, lastDate, campaignId);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), performances.getTotalPages());

        map.addAttribute("clientUser", clientUserWithCampaignsResponse);
        map.addAttribute("campaign", campaignWithCreativesResponse);
        map.addAttribute("creative", creativeWithPerformancesResponse);
        map.addAttribute("performances", performances);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("statistics", statistics);
        model.addAttribute("chart", chart);
        map.addAttribute("totalCount", performanceService.getPerformanceCount());

        return "manage/performance";
    }

    @GetMapping("/report")
    public ResponseEntity creativeReport(
            @PathVariable("campaignId") Long campaignId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {

        return ResponseEntity.ok(reportService.getPerformanceReport(response, campaignId, startDate, lastDate, ReportType.CREATIVE));
    }
}