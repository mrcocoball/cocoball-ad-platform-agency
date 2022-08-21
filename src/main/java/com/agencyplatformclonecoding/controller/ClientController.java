package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.service.ClientService;
import com.agencyplatformclonecoding.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("clients")
@Controller
public class ClientController {

    private final ClientService clientService;
    private final PaginationService paginationService;

    @GetMapping
    public String clients(ModelMap map) {
        map.addAttribute("clients", List.of());
        return "clients/index";
    }

    @GetMapping("/{clientId}")
    public String manageClient(@PathVariable String clientId) {
         return "forward:/manage/{clientId}/campaigns";
     }
}
