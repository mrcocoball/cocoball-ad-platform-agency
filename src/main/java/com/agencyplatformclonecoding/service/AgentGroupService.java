package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.repository.AgentGroupRepository;
import com.agencyplatformclonecoding.repository.AgentRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AgentGroupService {

    private final AgentRepository agentRepository;
    private final AgentGroupRepository agentGroupRepository;

}
