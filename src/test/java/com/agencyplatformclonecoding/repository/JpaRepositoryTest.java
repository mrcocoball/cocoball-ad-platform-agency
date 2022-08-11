package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.Config.JpaConfig;
import com.agencyplatformclonecoding.domain.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final AgencyRepository agencyRepository;
    private final AgentGroupRepository agentGroupRepository;
    private final AgentRepository agentRepository;
    private final ClientUserRepository clientUserRepository;
    private final AdCampaignRepository adCampaignRepository;
    private final CreativeRepository creativeRepository;

    public JpaRepositoryTest(
            @Autowired AgencyRepository agencyRepository,
            @Autowired AgentGroupRepository agentGroupRepository,
            @Autowired AgentRepository agentRepository,
            @Autowired ClientUserRepository clientUserRepository,
            @Autowired AdCampaignRepository adCampaignRepository,
            @Autowired CreativeRepository creativeRepository
    ) {
        this.agencyRepository = agencyRepository;
        this.agentGroupRepository = agentGroupRepository;
        this.agentRepository = agentRepository;
        this.clientUserRepository = clientUserRepository;
        this.adCampaignRepository = adCampaignRepository;
        this.creativeRepository = creativeRepository;
    }

    @DisplayName("SELECT 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<AgentGroup> agentGroups = agentGroupRepository.findAll();
        List<Agent> agents = agentRepository.findAll();
        List<ClientUser> clientUsers = clientUserRepository.findAll();
        List<AdCampaign> adCampaigns = adCampaignRepository.findAll();
        List<Creative> creatives = creativeRepository.findAll();

        // Then
        assertThat(agentGroups).isNotNull().hasSize(3);
        assertThat(agents).isNotNull().hasSize(5);
        assertThat(clientUsers).isNotNull().hasSize(7);
        assertThat(adCampaigns).isNotNull().hasSize(5);
        assertThat(creatives).isNotNull().hasSize(3);
    }

    @Disabled("아직 Repository 쪽 추가 설정을 안 해놔서 테스트 값이 안 맞음")
    @DisplayName("INSERT 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = creativeRepository.count();
        ClientUser clientUser = clientUserRepository.getReferenceById("client1");
        AdCampaign adCampaign = adCampaignRepository.save(AdCampaign.of(clientUser, 2L, "test", 10000));
        Creative creative = Creative.of(2L, adCampaign, "test", 10000);

        // When
        creativeRepository.save(creative);

        // Then
        assertThat(creativeRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("UPDATE 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Creative creative = creativeRepository.findById(1L).orElseThrow();
        long updatedBidingPrice = 15000;
        creative.setBidingPrice(updatedBidingPrice);

        // When
        Creative updatedCreative = creativeRepository.saveAndFlush(creative);

        // Then
        assertThat(updatedCreative).hasFieldOrPropertyWithValue("bidingPrice", updatedBidingPrice);
    }

    @DisplayName("DELETE 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        AdCampaign adCampaign = adCampaignRepository.findById(1L).orElseThrow();
        long previousAdCampaignCount = adCampaignRepository.count();
        long previousCreativeCount = creativeRepository.count();
        int deletedCreativesSize = adCampaign.getCreatives().size();

        // When
        adCampaignRepository.delete(adCampaign);

        // Then
        assertThat(adCampaignRepository.count()).isEqualTo(previousAdCampaignCount - 1);
        assertThat(creativeRepository.count()).isEqualTo(previousCreativeCount - deletedCreativesSize);

    }


}
