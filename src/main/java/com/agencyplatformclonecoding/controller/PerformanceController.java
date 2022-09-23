package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.CreativeService;
import com.agencyplatformclonecoding.service.ManageService;
import com.agencyplatformclonecoding.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping(value = "/manage/{clientId}/campaigns/{campaignId}/creatives/{creativeId}/performances")
@Controller
public class PerformanceController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final PerformanceService performanceService;

    // TODO :: 통계 / 보고서 관련 기능 추가 예정
}