package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.ReportType;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserWithCampaignsResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("manage")
@Controller
public class ManageController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final PaginationService paginationService;
    private final StatisticsService statisticsService;
    private final DashboardService dashboardService;
    private final ReportService reportService;

    @GetMapping()
    public String manage(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            @PageableDefault(size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            ModelMap map
    ) {
        Page<ClientUserResponse> clientUsers = manageService.searchClientUsers(searchType, searchValue, pageable)
                .map(ClientUserResponse::from);
        List<PerformanceStatisticsDto> totalClientSpendStatistics = statisticsService.getTotalSpendStatistics(startDate, lastDate);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), clientUsers.getTotalPages());

        map.addAttribute("clientUsers", clientUsers);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("totalClientSpendStatistics", totalClientSpendStatistics);
        map.addAttribute("searchTypes", SearchType.values());

        return "manage/index";
    }

    @GetMapping("/{clientId}/campaigns")
    public String campaigns(
            @PathVariable String clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            @PageableDefault(size = 5, sort = "activated", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map,
            Model model
    ) {
        Page<CampaignResponse> campaigns = campaignService.searchCampaigns(pageable, clientId).map(CampaignResponse::from);
        List<PerformanceStatisticsDto> campaignStatistics = statisticsService.getCampaignStatistics(startDate, lastDate, clientId);
        List<PerformanceStatisticsDto> totalCampaignStatistics = statisticsService.getTotalCampaignStatistics(startDate, lastDate, clientId);
        ClientUserWithCampaignsResponse clientUserWithCampaigns = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));
        List<DashboardStatisticsDto> chart = dashboardService.setCampaignPerformanceChart(startDate, lastDate, clientId);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), campaigns.getTotalPages());

        map.addAttribute("clientUser", clientUserWithCampaigns);
        map.addAttribute("campaigns", campaigns);
        map.addAttribute("campaignStatistics", campaignStatistics);
        map.addAttribute("totalCampaignStatistics", totalCampaignStatistics);
        map.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("chart", chart);
        map.addAttribute("totalCount", campaignService.getCampaignCount());

        return "manage/campaign";
    }

    @GetMapping("/report")
    public ResponseEntity clientReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getPerformanceReport(response, startDate, lastDate, ReportType.CLIENT));
    }

    @GetMapping("/spendReport")
    public ResponseEntity clientSpendReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getSpendReport(response, startDate, lastDate, ReportType.CLIENT));
    }

}
