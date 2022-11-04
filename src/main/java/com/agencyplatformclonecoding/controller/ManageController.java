package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.ReportType;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.domain.constrant.StatisticsType;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserResponse;
import com.agencyplatformclonecoding.dto.response.ClientUserWithCampaignsResponse;
import com.agencyplatformclonecoding.dto.response.PerformanceStatisticsResponse;
import com.agencyplatformclonecoding.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("manage")
@Controller
public class ManageController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final PaginationService paginationService;
    private final StatisticsService statisticsService;
    private final ReportService reportService;

    @GetMapping()
    public String manage(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) StatisticsType statisticsType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            @PageableDefault(size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            ModelMap map
    ) {
        Page<ClientUserResponse> clientUsers = manageService.searchClientUsers(searchType, searchValue, pageable)
                .map(ClientUserResponse::from);
        Set<PerformanceStatisticsResponse> totalClientSpendStatistics = statisticsService.getTotalSpendStatistics(startDate, lastDate, statisticsType)
                .stream().map(PerformanceStatisticsResponse::from).collect(Collectors.toCollection(LinkedHashSet::new));
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
            @RequestParam(required = false) StatisticsType statisticsType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            @PageableDefault(size = 5, sort = "activated", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<CampaignResponse> campaigns = campaignService.searchCampaigns(pageable, clientId).map(CampaignResponse::from);
        Set<PerformanceStatisticsResponse> campaignStatistics = statisticsService.getCampaignStatistics(startDate, lastDate, statisticsType, clientId)
                .stream().map(PerformanceStatisticsResponse::from).collect(Collectors.toCollection(LinkedHashSet::new));
        Set<PerformanceStatisticsResponse> totalCampaignStatistics = statisticsService.getTotalCampaignStatistics(startDate, lastDate, statisticsType, clientId)
                .stream().map(PerformanceStatisticsResponse::from).collect(Collectors.toCollection(LinkedHashSet::new));
        ClientUserWithCampaignsResponse clientUserWithCampaigns = ClientUserWithCampaignsResponse.from(manageService.getClientUserWithCampaigns(clientId));
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), campaigns.getTotalPages());

        map.addAttribute("clientUser", clientUserWithCampaigns);
        map.addAttribute("campaigns", campaigns);
        map.addAttribute("campaignStatistics", campaignStatistics);
        map.addAttribute("totalCampaignStatistics", totalCampaignStatistics);
        map.addAttribute("paginationBarNumbers", barNumbers);
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
