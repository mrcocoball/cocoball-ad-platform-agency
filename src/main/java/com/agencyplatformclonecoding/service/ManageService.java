package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.CampaignRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import com.agencyplatformclonecoding.repository.CreativeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ManageService {

    private final AgentRepository agentRepository;
    private final ClientUserRepository clientUserRepository;
    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;

}
