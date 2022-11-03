package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.ReportType;
import com.agencyplatformclonecoding.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RequiredArgsConstructor
@RequestMapping(value = "/manage/{clientId}/campaigns/{campaignId}/creatives/{creativeId}/performances")
@Controller
public class PerformanceController {

    private final ReportService reportService;

    @GetMapping("/report")
    public ResponseEntity performanceReport(
            @PathVariable("creativeId") Long creativeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(reportService.getPerformanceStatistics(response, creativeId, startDate, lastDate, ReportType.PERFORMANCE));
    }
}