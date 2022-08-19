package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.AgentGroupService;
import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/agentGroups")
@Controller
public class AgentGroupController {

    private final AgentGroupService agentGroupService;
    private final PaginationService paginationService;

    @GetMapping
    public String agentGroups(ModelMap map) {
        map.addAttribute("agentGroups", List.of());
        return "agentgroups/index";
    }

    @GetMapping("/{agentGroupId}")
    public String agentGroup(@PathVariable String agentGroupId, ModelMap map) {
        map.addAttribute("agentGroup", "agentGroup"); // TODO : 실제 데이터 구현 시 여기에 넣어야 함
        map.addAttribute("agents", List.of());
         return "agentgroups/detail";
    }

    @GetMapping("/{agentGroupId}/{agentId}")
        public String selectAgent(@PathVariable String agentGroupId, String agentId) {
             return "forward:/agents/{agentId}";
         }

}
