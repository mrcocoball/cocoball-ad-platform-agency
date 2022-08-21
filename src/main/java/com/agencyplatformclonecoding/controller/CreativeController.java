package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.CreativeService;
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
@RequestMapping(value = "/manage/{clientId:.+}/campaigns/{campaignId:.+}/creatives", method= RequestMethod.GET)
@Controller
public class CreativeController {

    private CreativeService creativeService;
    private PaginationService paginationService;

    @GetMapping
    public String creatives(
            @PathVariable("clientId") String clientId,
            @PathVariable("campaignId") Long campaignId, ModelMap map) {
        map.addAttribute("creatives", List.of());
        return "manage/creative";
    }

}
