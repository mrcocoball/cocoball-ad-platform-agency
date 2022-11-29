package com.agencyplatformclonecoding.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/agency")
@Controller
public class AgencyController {

    @RequestMapping(value={"/", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginGet(Model model) {
        return "agency/login";
    }
}
