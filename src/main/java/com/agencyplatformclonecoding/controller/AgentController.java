package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.response.AgentResponse;
import com.agencyplatformclonecoding.dto.response.AgentWithClientsResponse;
import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/agents")
@Controller
public class AgentController {

    private final AgentService agentService;
    private final PaginationService paginationService;

    @GetMapping
    public String agents(
                @RequestParam(required = false) SearchType searchType,
                @RequestParam(required = false) String searchValue,
                @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                ModelMap map
    	) {
    		Page<AgentResponse> agents = agentService.searchAgents(searchType, searchValue, pageable)
    				.map(AgentResponse::from);
    		List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), agents.getTotalPages());
            map.addAttribute("agents", agents);
    		map.addAttribute("paginationBarNumbers", barNumbers);
    		map.addAttribute("searchTypes", SearchType.values());

        return "agents/index";
    }

    @GetMapping("/{agentId}")
    public String agent(@PathVariable String agentId, ModelMap map) {
    		AgentWithClientsResponse agent = AgentWithClientsResponse.from(agentService.getAgentWithClients(agentId));

            map.addAttribute("agent", agent);
            map.addAttribute("clients", agent.clientUserResponses());
    		map.addAttribute("totalCount", agentService.getAgentCount());

         return "agents/detail";
    }
    @GetMapping("/{agentId}/{clientId}")
    public String mappingClient(@PathVariable String agentId, String clientId) {
         return "forward:/manage/{clientId}";
     }

    @PostMapping ("/{agentId}/delete")
   	public String deleteAgent(@PathVariable String agentId) {
   		agentService.deleteAgent(agentId);

   		return "redirect:/agents";

   	}

}
