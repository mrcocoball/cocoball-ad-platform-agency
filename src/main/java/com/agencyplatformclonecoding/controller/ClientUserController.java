package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.domain.constrant.SearchType;
import com.agencyplatformclonecoding.dto.response.ClientUserResponse;
import com.agencyplatformclonecoding.service.ClientUserService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("clients")
@Controller
public class ClientUserController {

    private final ClientUserService clientUserService;
    private final PaginationService paginationService;

    @GetMapping
    public String clientUsers(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<ClientUserResponse> clientUsers = clientUserService.searchClientUsers(searchType, searchValue, pageable)
                .map(ClientUserResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), clientUsers.getTotalPages());
        map.addAttribute("clientUsers", clientUsers);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "clients/index";
    }

    @GetMapping("/{clientId}")
    public String clientUser(@PathVariable String clientId, ModelMap map) {
        ClientUserResponse clientUser = ClientUserResponse.from(clientUserService.getClientUser(clientId));

        map.addAttribute("clientUser", clientUser);
        map.addAttribute("totalCount", clientUserService.getClientUserCount());

        return "clients/detail";
    }

    @GetMapping("/{clientId}/manage")
    public String manageClientUser(@PathVariable String clientId) {
        return "forward:/manage/{clientId}/campaigns";
    }
}
