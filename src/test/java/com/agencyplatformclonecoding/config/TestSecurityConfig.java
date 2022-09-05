package com.agencyplatformclonecoding.config;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private AgencyRepository agencyRepository;

    @BeforeTestMethod
    public void securitySetup() {
        given(agencyRepository.findById(anyString())).willReturn(Optional.of(Agency.of(
            "TestAgency",
            "pw",
            "test"
        )));
    }
}
