package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.config.TestSecurityConfig;
import com.agencyplatformclonecoding.service.CreativeService;
import com.agencyplatformclonecoding.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("VIEW 컨트롤러 - 소재 관리")
@Import(TestSecurityConfig.class)
@WebMvcTest(CreativeController.class)
class CreativeControllerTest {

    private final MockMvc mvc;

    @MockBean private CreativeService creativeService;

    @MockBean private PaginationService paginationService;

    public CreativeControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }
}
