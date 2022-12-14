package com.agencyplatformclonecoding.config;

import com.agencyplatformclonecoding.dto.AgencyDto;
import com.agencyplatformclonecoding.dto.security.CustomAuthFailureHandler;
import com.agencyplatformclonecoding.dto.security.PlatformPrincipal;
import com.agencyplatformclonecoding.repository.AgencyRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/agency/login")
                .loginProcessingUrl("/agency/login")
                .defaultSuccessUrl("/clients", true)
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(AgencyRepository agencyRepository) {
        return username -> agencyRepository
                .findById(username)
                .map(AgencyDto::from)
                .map(PlatformPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("????????? ?????? ??? ???????????? - username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthFailureHandler();
    }

}