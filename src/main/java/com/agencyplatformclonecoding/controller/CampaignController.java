package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.domain.constrant.ReportType;
import com.agencyplatformclonecoding.dto.ClientUserDto;
import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.dto.request.CampaignRequest;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserWithCampaignsResponse;
import com.agencyplatformclonecoding.dto.response.CreativeResponse;
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
@RequestMapping(value = "/manage/{clientId}/campaigns")
@Controller
public class CampaignController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final PaginationService paginationService;
    private final StatisticsService statisticsService;
    private final DashboardService dashboardService;
    private final ReportService reportService;

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
        campaignService.validateClientAndCampaign(campaignId, clientId);
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
        campaignService.updateCampaign(campaignId, clientId, campaignRequest.toDto(ClientUserDto.of(clientId)));

        return "redirect:/manage/{clientId}/campaigns";
    }

    @PostMapping("/{campaignId}/delete")
    public String deleteCampaign(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId
    ) {
        campaignService.deleteCampaign(campaignId, clientId);

        return "redirect:/manage/{clientId}/campaigns";
    }

    @GetMapping("/{campaignId}/creatives")
    public String creatives(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId,
            @PageableDefault(size = 5, sort = "activated", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            ModelMap map,
            Model model
    ) {
        Page<CreativeResponse> creatives = creativeService.searchCreatives(pageable, campaignId, clientId).map(CreativeResponse::from);
        List<PerformanceStatisticsDto> creativesStatistics = statisticsService.getCreativeStatistics(startDate, lastDate, campaignId);
        List<PerformanceStatisticsDto> totalCreativesStatistics = statisticsService.getTotalCreativeStatistics(startDate, lastDate, campaignId);
        CampaignResponse campaign = CampaignResponse.from(campaignService.getCampaign(campaignId));
        ClientUserWithCampaignsResponse clientUserWithCampaignsResponse = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));
        List<DashboardStatisticsDto> chart = dashboardService.setCreativePerformanceChart(startDate, lastDate, campaignId);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), creatives.getTotalPages());

        map.addAttribute("clientUser", clientUserWithCampaignsResponse);
        map.addAttribute("campaign", campaign);
        map.addAttribute("creatives", creatives);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("creativeStatistics", creativesStatistics);
        map.addAttribute("totalCreativeStatistics", totalCreativesStatistics);
        model.addAttribute("chart", chart);
        map.addAttribute("totalCount", creativeService.getCreativeCount());

        return "manage/creative";
    }

    @PostMapping("/{campaignId}/activate")
    public String activateToggle(
            @PathVariable("clientId") String clientId,
            @PathVariable Long campaignId
    ) {
        campaignService.toggleCampaignActivate(campaignId, clientId);

        return "redirect:/manage/{clientId}/campaigns";
    }

    @GetMapping("/report")
    public ResponseEntity campaignReport(
            @PathVariable("clientId") String clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getPerformanceReport(response, clientId, startDate, lastDate, ReportType.CAMPAIGN));
    }
}
