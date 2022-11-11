package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.service.DashboardService;
import com.agencyplatformclonecoding.service.PaginationService;
import com.agencyplatformclonecoding.service.ReportService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("dashboard")
@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final ReportService reportService;
    private final PaginationService paginationService;

    @GetMapping()
    public String dashboard(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            Model model,
            ModelMap map,
            @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<DashboardStatisticsDto> results = dashboardService.setSpendChart(startDate, lastDate);
        Page<DashboardStatisticsDto> resultpages = dashboardService.setSpendChartList(startDate, lastDate, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), resultpages.getTotalPages());

        model.addAttribute("results", results);
        model.addAttribute("resultpages", resultpages);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "dashboard/index";
    }

    @GetMapping("/clients")
    public String dashboardClients(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            Model model,
            ModelMap map,
            @PageableDefault(size = 10, sort = "spend", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<DashboardStatisticsDto> results = dashboardService.setClientsSpendChart(startDate, lastDate);
        Page<DashboardStatisticsDto> resultpages = dashboardService.setClientsSpendChartList(startDate, lastDate, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), resultpages.getTotalPages());

        model.addAttribute("results", results);
        model.addAttribute("resultpages", resultpages);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "dashboard/clients";
    }

    @GetMapping("/agents")
    public String dashboardAgents(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            Model model,
            ModelMap map,
            @PageableDefault(size = 10, sort = "spend", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<DashboardStatisticsDto> results = dashboardService.setAgentsSpendChart(startDate, lastDate);
        Page<DashboardStatisticsDto> resultpages = dashboardService.setAgentsSpendChartList(startDate, lastDate, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), resultpages.getTotalPages());

        model.addAttribute("results", results);
        model.addAttribute("resultpages", resultpages);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "dashboard/agents";
    }

    @GetMapping("/groups")
    public String dashboardGroups(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            Model model,
            ModelMap map,
            @PageableDefault(size = 10, sort = "spend", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<DashboardStatisticsDto> results = dashboardService.setGroupsSpendChart(startDate, lastDate);
        Page<DashboardStatisticsDto> resultpages = dashboardService.setGroupsSpendChartList(startDate, lastDate, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), resultpages.getTotalPages());

        model.addAttribute("results", results);
        model.addAttribute("resultpages", resultpages);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "dashboard/groups";
    }

    @GetMapping("/category")
    public String dashboardCategory(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            Model model,
            ModelMap map,
            @PageableDefault(size = 10, sort = "spend", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<DashboardStatisticsDto> results = dashboardService.setCategoryChart(startDate, lastDate);
        Page<DashboardStatisticsDto> resultpages = dashboardService.setCategoryChartList(startDate, lastDate, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), resultpages.getTotalPages());

        model.addAttribute("results", results);
        model.addAttribute("resultpages", resultpages);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "dashboard/category";
    }

    @GetMapping("/reference")
    public String dashboardReference(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            Model model,
            ModelMap map,
            @PageableDefault(size = 10, sort = "spend", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<DashboardStatisticsDto> results = dashboardService.setReferenceChart(startDate, lastDate);
        Page<DashboardStatisticsDto> resultpages = dashboardService.setReferenceChartList(startDate, lastDate, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), resultpages.getTotalPages());

        model.addAttribute("results", results);
        model.addAttribute("resultpages", resultpages);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "dashboard/reference";
    }

    @GetMapping("/report")
    public ResponseEntity totalSpendReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getDashboardTotalSpendReport(response, startDate, lastDate));
    }

    @GetMapping("/clients/report")
    public ResponseEntity clientsReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getDashboardClientsSpendReport(response, startDate, lastDate));
    }

    @GetMapping("/agents/report")
    public ResponseEntity agentsReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getDashboardAgentsSpendReport(response, startDate, lastDate));
    }

    @GetMapping("/groups/report")
    public ResponseEntity groupsReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getDashboardGroupsSpendReport(response, startDate, lastDate));
    }

    @GetMapping("/category/report")
    public ResponseEntity categoryReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getDashboardCategorySpendReport(response, startDate, lastDate));
    }

    @GetMapping("/reference/report")
    public ResponseEntity referenceReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getDashboardCategoryReferenceReport(response, startDate, lastDate));
    }
}
