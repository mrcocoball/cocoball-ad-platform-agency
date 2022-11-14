package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final AgencyRepository agencyRepository;
    private final AgentGroupRepository agentGroupRepository;
    private final AgentRepository agentRepository;
    private final CategoryRepository categoryRepository;
    private final ClientUserRepository clientUserRepository;
    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;
    private final PerformanceRepository performanceRepository;

    public JpaRepositoryTest(
            @Autowired AgencyRepository agencyRepository,
            @Autowired AgentGroupRepository agentGroupRepository,
            @Autowired AgentRepository agentRepository,
            @Autowired CategoryRepository categoryRepository,
            @Autowired ClientUserRepository clientUserRepository,
            @Autowired CampaignRepository campaignRepository,
            @Autowired CreativeRepository creativeRepository,
            @Autowired PerformanceRepository performanceRepository
    ) {
        this.agencyRepository = agencyRepository;
        this.agentGroupRepository = agentGroupRepository;
        this.agentRepository = agentRepository;
        this.categoryRepository = categoryRepository;
        this.clientUserRepository = clientUserRepository;
        this.campaignRepository = campaignRepository;
        this.creativeRepository = creativeRepository;
        this.performanceRepository = performanceRepository;
    }

    @DisplayName("SELECT 테스트 - 에이전트 그룹 / 에이전트 / 광고주 / 캠페인 / 소재")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<AgentGroup> agentGroups = agentGroupRepository.findAll();
        List<Agent> agents = agentRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        List<ClientUser> clientUsers = clientUserRepository.findAll();
        List<Campaign> campaigns = campaignRepository.findAll();
        List<Creative> creatives = creativeRepository.findAll();
        List<Performance> performances = performanceRepository.findAll();

        // Then
        assertThat(agentGroups).isNotNull().hasSize(6);
        assertThat(agents).isNotNull().hasSize(21);
        assertThat(categories).isNotNull().hasSize(26);
        assertThat(clientUsers).isNotNull().hasSize(40);
        assertThat(campaigns).isNotNull().hasSize(45);
        assertThat(creatives).isNotNull().hasSize(53);
        assertThat(performances).isNotNull().hasSize(1298);
    }

    @DisplayName("INSERT 테스트 - 에이전트 그룹")
    @Test
    void givenTestAgentGroupData_whenAgentGroupInserting_thenWorksFine() {
        // Given
        long previousCount = agentGroupRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();

        // When
        AgentGroup savedAgentGroup = agentGroupRepository.save(AgentGroup.of(agency, "testgroup"));

        // Then
        assertThat(agentGroupRepository.count()).isEqualTo(previousCount + 1);
    }
    @DisplayName("INSERT 테스트 - 에이전트")
    @Test
    void givenTestAgentData_whenAgentInserting_thenWorksFine() {
        // Given
        long previousCount = agentRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();
        AgentGroup agentGroup = agentGroupRepository.findById(1L).orElseThrow();
        Agent agent = Agent.of(agency, agentGroup, "test", "pw", "email@mail.com", "테스트");

        // When
        agentRepository.save(agent);

        // Then
        assertThat(agentRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("INSERT 테스트 - 카테고리")
    @Test
    void givenTestCategoryData_whenCategoryInserting_thenWorksFine() {
        // Given
        long previousCount = categoryRepository.count();
        Category category = Category.of("t-category");

        // When
        categoryRepository.save(category);

        // Then
        assertThat(categoryRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("INSERT 테스트 - 광고주")
    @Test
    void givenTestClientData_whenClientInserting_thenWorksFine() {
        // Given
        long previousCount = clientUserRepository.count();
        Agency agency = agencyRepository.findById("TestAgency").orElseThrow();
        Agent agent = agentRepository.findById("agent1").orElseThrow();
        Category category = categoryRepository.findById(1L).orElseThrow();
        ClientUser clientUser = ClientUser.of(agency, agent, category,"testclient", "pw", "email@mail.com", "김봉식");

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
        Category category = categoryRepository.findById(1L).orElseThrow();
        ClientUser clientUser = ClientUser.of(agency, agent, category, "testclient", "pw", "email@mail.com", "김봉식");
        clientUserRepository.save(clientUser);
        Campaign campaign = Campaign.of(clientUser, "testxxx", 22222L);

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
        Category category = categoryRepository.findById(1L).orElseThrow();
        ClientUser clientUser = ClientUser.of(agency, agent, category, "testclient", "pw", "email@mail.com", "김봉식");
        clientUserRepository.save(clientUser);
        Campaign campaign = Campaign.of(clientUser, "testxxx", 22222L);
        campaignRepository.save(campaign);
        Creative creative = Creative.of(campaign, "초특가할인", 10000L, "description", "url");

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
        ClientUser clientUser = clientUserRepository.findById("c01").orElseThrow();
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

    @EnableJpaAuditing
    @TestConfiguration
    static class TestJpaConfig {
        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("TestAgency");
        }
    }
}
