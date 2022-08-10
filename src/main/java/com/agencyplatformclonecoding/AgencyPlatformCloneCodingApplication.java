package com.agencyplatformclonecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AgencyPlatformCloneCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgencyPlatformCloneCodingApplication.class, args);
    }

}
