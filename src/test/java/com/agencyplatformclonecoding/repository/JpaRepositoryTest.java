package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.Config.JpaConfig;
import com.agencyplatformclonecoding.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final AgencyRepository agencyRepository;
    private final AgentGroupRepository agentGroupRepository;
    private final AgentRepository agentRepository;
    private final ClientUserRepository clientUserRepository;
    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;

    public JpaRepositoryTest(
            @Autowired AgencyRepository agencyRepository,
            @Autowired AgentGroupRepository agentGroupRepository,
            @Autowired AgentRepository agentRepository,
            @Autowired ClientUserRepository clientUserRepository,
            @Autowired CampaignRepository campaignRepository,
            @Autowired CreativeRepository creativeRepository
    ) {
        this.agencyRepository = agencyRepository;
        this.agentGroupRepository = agentGroupRepository;
        this.agentRepository = agentRepository;
        this.clientUserRepository = clientUserRepository;
        this.campaignRepository = campaignRepository;
        this.creativeRepository = creativeRepository;
    }

    @DisplayName("SELECT 테스트 - 에이전트 그룹 / 에이전트 / 광고주 / 캠페인 / 소재")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<AgentGroup> agentGroups = agentGroupRepository.findAll();
        List<Agent> agents = agentRepository.findAll();
        List<ClientUser> clientUsers = clientUserRepository.findAll();
        List<Campaign> campaigns = campaignRepository.findAll();
        List<Creative> creatives = creativeRepository.findAll();

        // Then
        assertThat(agentGroups).isNotNull().hasSize(3);
        assertThat(agents).isNotNull().hasSize(5);
        assertThat(clientUsers).isNotNull().hasSize(7);
        assertThat(campaigns).isNotNull().hasSize(5);
        assertThat(creatives).isNotNull().hasSize(3);
    }

    @DisplayName("INSERT 테스트 - 에이전트 그룹")
    @Test
    void givenTestAgentGroupData_whenAgentGroupInserting_thenWorksFine() {
        // Given
        long previousCount = agentGroupRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();

        // When
        AgentGroup savedAgentGroup = agentGroupRepository.save(AgentGroup.of(agency, "testgroup","testgroup"));

        // Then
        assertThat(agentGroupRepository.count()).isEqualTo(previousCount + 1);
    }
    @DisplayName("INSERT 테스트 - 에이전트")
    @Test
    void givenTestAgentData_whenAgentInserting_thenWorksFine() {
        // Given
        long previousCount = agentRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();
        AgentGroup agentGroup = agentGroupRepository.findById("마케팅 1팀").orElseThrow();
        Agent agent = Agent.of(agency, agentGroup, "test", "pw", "email@mail.com", "테스트");

        // When
        agentRepository.save(agent);

        // Then
        assertThat(agentRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("INSERT 테스트 - 광고주")
    @Test
    void givenTestClientData_whenClientInserting_thenWorksFine() {
        // Given
        long previousCount = clientUserRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();
        Agent agent = agentRepository.findById("agent1").orElseThrow();
        ClientUser clientUser = ClientUser.of(agency, agent, "testclient", "pw", "email@mail.com", "김봉식");

        // When
        clientUserRepository.save(clientUser);

        // Then
        assertThat(clientUserRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("INSERT 테스트 - 캠페인")
    @Test
    void givenTestCampaignData_whenCampaignInserting_thenWorksFine() {
        // Given
        long previousCount = campaignRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();
        Agent agent = agentRepository.findById("agent1").orElseThrow();
        ClientUser clientUser = ClientUser.of(agency, agent, "testclient", "pw", "email@mail.com", "김봉식");
        clientUserRepository.save(clientUser);
        Campaign campaign = Campaign.of(clientUser, "testxxx", 22222);

        // When
        campaignRepository.save(campaign);

        // Then
        assertThat(campaignRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("INSERT 테스트 - 소재")
    @Test
    void givenTestCreativeData_whenCreativeInserting_thenWorksFine() {
        // Given
        long previousCount = creativeRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();
        Agent agent = agentRepository.findById("agent1").orElseThrow();
        ClientUser clientUser = ClientUser.of(agency, agent, "testclient", "pw", "email@mail.com", "김봉식");
        clientUserRepository.save(clientUser);
        Campaign campaign = Campaign.of(clientUser, "testxxx", 22222);
        campaignRepository.save(campaign);
        Creative creative = Creative.of(campaign, "초특가할인", 10000);

        // When
        creativeRepository.save(creative);

        // Then
        assertThat(creativeRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("UPDATE 테스트 - 에이전트 내용 업데이트")
    @Test
    void givenTestAgentData_whenAgentUpdating_thenWorksFine() {
        // Given
        Agent agent = agentRepository.findById("agent1").orElseThrow();
        String updatedName = "update";
        agent.setNickname(updatedName);

        // When
        Agent updatedAgent = agentRepository.saveAndFlush(agent);

        // Then
        assertThat(updatedAgent).hasFieldOrPropertyWithValue("nickname", updatedName);
    }

    @DisplayName("UPDATE 테스트 - 광고주 내용 업데이트")
    @Test
    void givenTestClientData_whenClientUpdating_thenWorksFine() {
        // Given
        ClientUser clientUser = clientUserRepository.findById("client1").orElseThrow();
        String updatedName = "update";
        clientUser.setNickname(updatedName);

        // When
        ClientUser updatedClientUser = clientUserRepository.saveAndFlush(clientUser);

        // Then
        assertThat(updatedClientUser).hasFieldOrPropertyWithValue("nickname", updatedName);
    }

    @DisplayName("UPDATE 테스트 - 캠페인 내용 업데이트")
    @Test
    void givenTestCampaignData_whenCampaignUpdating_thenWorksFine() {
        // Given
        Campaign campaign = campaignRepository.findById(1L).orElseThrow();
        long updatedBudget = 15000;
        campaign.setBudget(updatedBudget);

        // When
        Campaign updatedCampaign = campaignRepository.saveAndFlush(campaign);

        // Then
        assertThat(updatedCampaign).hasFieldOrPropertyWithValue("budget", updatedBudget);
    }

    @DisplayName("UPDATE 테스트 - 소재 내용 업데이트")
    @Test
    void givenTestCreativeData_whenCreativeUpdating_thenWorksFine() {
        // Given
        Creative creative = creativeRepository.findById(1L).orElseThrow();
        long updatedBidingPrice = 15000;
        creative.setBidingPrice(updatedBidingPrice);

        // When
        Creative updatedCreative = creativeRepository.saveAndFlush(creative);

        // Then
        assertThat(updatedCreative).hasFieldOrPropertyWithValue("bidingPrice", updatedBidingPrice);
    }
    @DisplayName("DELETE 테스트 - 캠페인 삭제") // TODO : 실제로는 완전 삭제가 아니라 '삭제된 상태'로 변경해야 함
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Campaign campaign = campaignRepository.findById(1L).orElseThrow();
        long previousAdCampaignCount = campaignRepository.count();
        long previousCreativeCount = creativeRepository.count();
        int deletedCreativesSize = campaign.getCreatives().size();

        // When
        campaignRepository.delete(campaign);

        // Then
        assertThat(campaignRepository.count()).isEqualTo(previousAdCampaignCount - 1);
        assertThat(creativeRepository.count()).isEqualTo(previousCreativeCount - deletedCreativesSize);
    }

    @DisplayName("DELETE 테스트 - 소재 삭제") // TODO : 실제로는 완전 삭제가 아니라 '삭제된 상태'로 변경해야 함
    @Test
    void givenTestCreativeData_whenDeleting_thenWorksFine() {
        // Given
        Creative creative = creativeRepository.findById(1L).orElseThrow();
        long previousCreativeCount = creativeRepository.count();

        // When
        creativeRepository.delete(creative);

        // Then
        assertThat(creativeRepository.count()).isEqualTo(previousCreativeCount - 1);
    }
}
