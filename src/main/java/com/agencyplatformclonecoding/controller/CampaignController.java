package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.CampaignService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/manage/{clientId:.+}/campaigns", method= RequestMethod.GET)
@Controller
public class CampaignController {

    private final CampaignService campaignService;
    private final PaginationService paginationService;

    @GetMapping
    public String campaigns(@PathVariable("clientId") String clientId, ModelMap map) {
        map.addAttribute("campaigns", List.of());
        return "manage/campaign";
    }

}

