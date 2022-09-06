package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.FormStatus;
import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.request.AgentGroupRequest;
import com.agencyplatformclonecoding.dto.response.AgentGroupResponse;
import com.agencyplatformclonecoding.dto.response.AgentGroupWithAgentsResponse;
import com.agencyplatformclonecoding.dto.security.PlatformPrincipal;
import com.agencyplatformclonecoding.service.AgentGroupService;
import com.agencyplatformclonecoding.service.AgentService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/agentGroups")
@Controller
public class AgentGroupController {

    private final AgentGroupService agentGroupService;
    private final PaginationService paginationService;

    @GetMapping
    public String agentGroups(
                @RequestParam(required = false) SearchType searchType,
                @RequestParam(required = false) String searchValue,
                @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                ModelMap map
    	) {
    		Page<AgentGroupResponse> agentGroups = agentGroupService.searchAgentGroups(searchType, searchValue, pageable)
    				.map(AgentGroupResponse::from);
    		List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), agentGroups.getTotalPages());
            map.addAttribute("agentGroups", agentGroups);
    		map.addAttribute("paginationBarNumbers", barNumbers);
    		map.addAttribute("searchTypes", SearchType.values());

            return "agentgroups/index";
        }

    @GetMapping("/{agentGroupId}")
    public String agentGroup(@PathVariable Long agentGroupId, ModelMap map) {

		AgentGroupWithAgentsResponse agentGroup = AgentGroupWithAgentsResponse.from(agentGroupService.getAgentGroupWithAgents(agentGroupId));

        map.addAttribute("agentGroup", agentGroup);
        map.addAttribute("agents", agentGroup.agentResponses());
		map.addAttribute("totalCount", agentGroupService.getAgentGroupCount());

        return "agentgroups/detail";
    }

    @GetMapping("/{agentGroupId}/{agentId}")
    public String selectAgent(@PathVariable String agentGroupId, String agentId) {
        return "forward:/agents/{agentId}";
    }

    @GetMapping("/form")
   	public String agentGroupForm(ModelMap map) {
   		map.addAttribute("formStatus", FormStatus.CREATE);

   		return "agentgroups/form";
   	}

   	@PostMapping("/form")
   	public String createNewAgentGroup(
			   @AuthenticationPrincipal PlatformPrincipal platformPrincipal,
			   AgentGroupRequest agentGroupRequest) {
   		agentGroupService.saveAgentGroup(agentGroupRequest.toDto(platformPrincipal.toDto()));

   		return "redirect:/agentGroups";
   	}

   	@GetMapping("/{agentGroupId}/form")
   	public String updateAgentGroupForm(@PathVariable Long agentGroupId, ModelMap map) {
   		AgentGroupResponse agentGroup = AgentGroupResponse.from(agentGroupService.getAgentGroup(agentGroupId));

   		map.addAttribute("agentGroup", agentGroup);
   		map.addAttribute("formStatus", FormStatus.UPDATE);

   		return "agentgroups/form";
   	}

   	@PostMapping("/{agentGroupId}/form")
   	public String updateAgentGroup(
			   @PathVariable Long agentGroupId,
			   @AuthenticationPrincipal PlatformPrincipal platformPrincipal,
			   AgentGroupRequest agentGroupRequest) {
   		agentGroupService.updateAgentGroup(agentGroupId, agentGroupRequest.toDto(platformPrincipal.toDto()));  // TODO : 추후 에이전시 인증 기능 부여

   		return "redirect:/agentGroups/";
   	}

   	@PostMapping ("/{agentGroupId}/delete")
   	public String deleteAgentGroup(
			   @PathVariable Long agentGroupId,
			   @AuthenticationPrincipal PlatformPrincipal platformPrincipal) {
   		agentGroupService.deleteAgentGroup(agentGroupId, platformPrincipal.getUsername());

   		return "redirect:/agentGroups";
   	}

}
