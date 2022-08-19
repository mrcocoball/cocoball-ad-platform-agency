package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("manage")
@Controller
public class ManageController {

    private final ManageService manageService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final PaginationService paginationService;

    @GetMapping
    public String manage(ModelMap map) {
        map.addAttribute("manage", List.of());
        return "manage/index";
    }

    @GetMapping("/{clientId}")
     public String manageClient(@PathVariable String clientId, ModelMap map) {
        map.addAttribute("clientId", "clientId"); // TODO : 실제 데이터 구현 시 여기에 넣어야 함
        map.addAttribute("campaigns", List.of());
         return "manage/client";
     }

    @GetMapping("/{clientId}/{campaignId}")
     public String manageClientsCampaign(@PathVariable String clientId, Long campaignId, ModelMap map) {
        map.addAttribute("clientId", "clientId"); // TODO : 실제 데이터 구현 시 여기에 넣어야 함
        map.addAttribute("campaignId", "campaignId");
        map.addAttribute("creatives", List.of());
         return "manage/campaign";
     }

    @GetMapping("/{clientId}/{campaignId}/{creativeId}")
     public String manageClientsCreative(@PathVariable String clientId, Long campaignId, Long creativeId, ModelMap map) {
        map.addAttribute("clientId", "clientId"); // TODO : 실제 데이터 구현 시 여기에 넣어야 함
        map.addAttribute("campaignId", "campaignId");
        map.addAttribute("creativeId", "creativeId");
         return "manage/creative";
     }

}
