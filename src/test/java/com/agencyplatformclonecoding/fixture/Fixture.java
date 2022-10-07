package com.agencyplatformclonecoding.fixture;

import com.agencyplatformclonecoding.domain.*;
import com.agencyplatformclonecoding.dto.*;

import java.time.LocalDateTime;
import java.util.Set;

public class Fixture {

    public static Agency createAgency() {
        Agency agency = Agency.of(
                "t-agency",
                "pw",
                "테스트용"
        );

        return agency;
    }

    public static AgentGroup createAgentGroup() {
        AgentGroup agentGroup = AgentGroup.of(
                createAgency(),
                1L,
                "테스트용그룹"
        );

        return agentGroup;
    }

    public static Agent createAgent() {
        Agent agent = Agent.of(
                createAgency(),
                createAgentGroup(),
                "t-agent",
                "pw",
                "email",
                "테스트용"
        );

        return agent;
    }

    public static Category createCategory() {
        Category category = Category.of(
                "t-category"
        );

        return category;
    }

    public static ClientUser createClientUser() {
        ClientUser clientUser = ClientUser.of(
                createAgency(),
                createAgent(),
                createCategory(),
                "t-client",
                "pw",
                "email",
                "테스트용"
        );

        return clientUser;
    }

    public static Campaign createCampaign() {
        Campaign campaign = Campaign.of(
                createClientUser(),
                "t-campaign",
                10000L
        );

        return campaign;
    }

    public static Creative createCreative() {
        Creative creative = Creative.of(
                createCampaign(),
                "t-keyword",
                1000L
        );

        return creative;
    }

    public static AgencyDto createAgencyDto() {
        return AgencyDto.of(
                "t-agency",
                "pw",
                "테스트용"
        );
    }

    public static AgentGroupDto createAgentGroupDto() {
        return AgentGroupDto.of(
                createAgencyDto(),
                1L,
                "테스트용",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    public static AgentGroupDto createAgentGroupDto(String agentGroupName) {
        return AgentGroupDto.of(
                createAgencyDto(),
                1L,
                agentGroupName,
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    public static AgentGroupWithAgentsDto createAgentGroupWithAgentsDto() {
        return AgentGroupWithAgentsDto.of(
                createAgencyDto(),
                Set.of(),
                1L,
                "김테스트",
                LocalDateTime.now(),
                "김테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    public static AgentDto createAgentDto() {
        return AgentDto.of(
                createAgencyDto(),
                createAgentGroupDto(),
                "t-agent",
                "pw",
                "테스트용용",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    public static AgentWithClientsDto createAgentWithClientsDto() {
        return AgentWithClientsDto.of(
                createAgencyDto(),
                createAgentGroupDto(),
                Set.of(),
                "t-client",
                "pw",
                "김테스트",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트"
        );
    }

    public static CategoryDto createCategoryDto() {
        return CategoryDto.of(
                1L,
                "t-category",
                LocalDateTime.now(),
                "test",
                LocalDateTime.now(),
                "test"
        );
    }

    public static ClientUserDto createClientUserDto() {
        return ClientUserDto.of(
                createAgencyDto(),
                createAgentDto(),
                createCategoryDto(),
                "t-client",
                "pw",
                "테스트용",
                "email",
                LocalDateTime.now(),
                "테스트용",
                LocalDateTime.now(),
                "테스트용"
        );
    }

    public static ClientUserWithCampaignsDto createClientUserWithCampaignsDto() {
        return ClientUserWithCampaignsDto.of(
                createAgencyDto(),
                createAgentDto(),
                createCategoryDto(),
                "t-client",
                "pw",
                "테스트용",
                "email",
                LocalDateTime.now(),
                "테스트",
                LocalDateTime.now(),
                "테스트",
                Set.of()
        );
    }

    public static CampaignDto createCampaignDto() {
        return createCampaignDto("original");
    }

    public static CampaignDto createCampaignDto(String name) {
        return CampaignDto.of(
                createClientUserDto(),
                1L,
                name,
                10000L,
                "10000",
                LocalDateTime.now(),
                "테스트용",
                LocalDateTime.now(),
                "테스트용",
                false
        );
    }

    public static CampaignDto createCampaignDto(Long budget) {
        return CampaignDto.of(
                createClientUserDto(),
                1L,
                "t-campaign",
                budget,
                "budget",
                LocalDateTime.now(),
                "테스트용",
                LocalDateTime.now(),
                "테스트용",
                false
        );
    }

    public static CreativeDto createCreativeDto() {
        return createCreativeDto("original");
    }

    public static CreativeDto createCreativeDto(String keyword) {
        return CreativeDto.of(
                createCampaignDto(),
                1L,
                keyword,
                1000L
        );
    }

    public static CreativeDto createCreativeDto(Long bidingPrice) {
        return CreativeDto.of(
                createCampaignDto(),
                1L,
                "t-creative",
                bidingPrice
        );
    }


}
