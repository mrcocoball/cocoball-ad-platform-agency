package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/agents")
@Controller
public class AgentController {

    private final AgentService agentService;
    private final PaginationService paginationService;

    @GetMapping
    public String agents(ModelMap map) {
        map.addAttribute("agents", List.of());
        return "agents/index";
    }

    @GetMapping("/{agentId}")
    public String agent(@PathVariable String agentId, ModelMap map) {
        map.addAttribute("agent", "agent"); // TODO : 실제 데이터 구현 시 여기에 넣어야 함
        map.addAttribute("clients", List.of());
         return "agents/detail";
    }
    @GetMapping("/{agentId}/{clientId}")
    public String mappingClient(@PathVariable String agentId, String clientId) {
         return "forward:/manage/{clientId}";
     }

}
