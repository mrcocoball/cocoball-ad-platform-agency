package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.dto.CreativeDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.fixture.Fixture;
import com.agencyplatformclonecoding.repository.CampaignRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import com.agencyplatformclonecoding.repository.CreativeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 소재")
@ExtendWith(MockitoExtension.class)
public class CreativeServiceTest {

    @InjectMocks private CreativeService sut;

    @Mock private ClientUserRepository clientUserRepository;
    @Mock private CampaignRepository campaignRepository;
    @Mock private CreativeRepository creativeRepository;

    @DisplayName("READ - 소재 조회 시 소재 반환")
    @Test
    void givenCreativeId_whenSearchingCreative_thenReturnsCreative() {
        // Given
        Long creativeId = 1L;
        Creative creative = Fixture.createCreative();
        given(creativeRepository.findByIdAndDeletedFalse(creativeId)).willReturn(Optional.of(creative));

        // When
        CreativeDto dto = sut.getCreative(creativeId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("keyword", creative.getKeyword())
                .hasFieldOrPropertyWithValue("bidingPrice", creative.getBidingPrice());
        then(creativeRepository).should().findByIdAndDeletedFalse(creativeId);
    }

    @DisplayName("READ - 소재 리스트 조회 시 소재 리스트 반환")
    @Test
    void givenNothing_whenSearchingCreatives_thenReturnsCreatives() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        Long campaignId = 1L;
        String clientId = "t-client";
        given(creativeRepository.findByDeletedFalseAndCampaign_Id(pageable, campaignId)).willReturn(Page.empty());

        // When
        Page<CreativeDto> creatives = sut.searchCreatives(pageable, campaignId, clientId);

        // Then
        assertThat(creatives).isEmpty();
        then(creativeRepository).should().findByDeletedFalseAndCampaign_Id(pageable, campaignId);
    }

    @DisplayName("READ - 소재가 존재하지 않을 경우 예외 처리")
    @Test
    void givenNonexistentCreativeId_whenSearchingCreative_thenThrowsException() {
        // Given
        Long creativeId = 0L;
        given(creativeRepository.findByIdAndDeletedFalse(creativeId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getCreative(creativeId));

        // Then
        assertThat(t)
                .isInstanceOf(AdPlatformException.class);
        then(creativeRepository).should().findByIdAndDeletedFalse(creativeId);
    }

    @DisplayName("CREATE - 소재 정보 입력 시 소재 생성")
    @Test
    void givenCreativeInfo_whenSavingCreative_thenSavesCreative() {
        // Given
        CreativeDto dto = Fixture.createCreativeDto();
        given(campaignRepository.getReferenceById(dto.campaignDto().id())).willReturn(Fixture.createCampaign());
        given(creativeRepository.save(any(Creative.class))).willReturn(Fixture.createCreative());

        // When
        sut.saveCreative(dto);

        // Then
        then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
        then(creativeRepository).should().save(any(Creative.class));
    }

    @DisplayName("CREATE - 소재 생성을 시도하였으나 일치하는 캠페인이 없을 경우 경고 로그 출력")
    @Test
    void givenNotExistCreative_whenSavingCreative_thenLogsWarningAndDoesNothing() {
        // Given
        CreativeDto dto = Fixture.createCreativeDto("update-campaign");
        given(campaignRepository.getReferenceById(dto.campaignDto().id())).willThrow(EntityNotFoundException.class);

        // When
        sut.saveCreative(dto);

        // Then
        then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
        then(creativeRepository).shouldHaveNoInteractions();
    }

    @DisplayName("UPDATE - 소재 키워드 수정 요청 시 소재 키워드 수정")
    @Test
    void givenCreativeModifiedKeyword_whenUpdatingCreativeKeyword_thenUpdatesCreativeKeyword() {
        // Given
        ClientUser clientUser = Fixture.createClientUser();
        Creative creative = Fixture.createCreative();
        CreativeDto dto = Fixture.createCreativeDto("t-keyword");
        given(campaignRepository.getReferenceById(dto.campaignDto().id())).willReturn(dto.campaignDto().toEntity(clientUser));
        given(creativeRepository.getReferenceById(dto.id())).willReturn(creative);

        // When
        sut.updateCreative(dto.id(), dto.campaignDto().id(), dto.campaignDto().clientUserDto().userId(), dto);

        // Then
        assertThat(creative)
                .hasFieldOrPropertyWithValue("keyword", dto.keyword());
        then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
        then(creativeRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("UPDATE - 소재 입찰가 수정 요청 시 소재 입찰가 수정")
    @Test
    void givenCreativeModifiedBidingPrice_whenUpdatingCreativeBidingPrice_thenUpdatesCreativeBidingPrice() {
        // Given
        ClientUser clientUser = Fixture.createClientUser();
        Creative creative = Fixture.createCreative();
        CreativeDto dto = Fixture.createCreativeDto(2000L);
        given(campaignRepository.getReferenceById(dto.campaignDto().id())).willReturn(dto.campaignDto().toEntity(clientUser));
        given(creativeRepository.getReferenceById(dto.id())).willReturn(creative);

        // When
        sut.updateCreative(dto.id(), dto.campaignDto().id(), dto.campaignDto().clientUserDto().userId(), dto);

        // Then
        assertThat(creative)
                .hasFieldOrPropertyWithValue("bidingPrice", dto.bidingPrice());
        then(campaignRepository).should().getReferenceById(dto.campaignDto().id());
        then(creativeRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("UPDATE - 존재하지 않는 소재 수정 요청 시 경고 로그 출력")
    @Test
    void givenNotExistCreative_whenUpdatingCreativeKeyword_thenLogsWarningAndDoesNothing() {
        // Given
        CreativeDto dto = Fixture.createCreativeDto("update-campaign");
        given(creativeRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateCreative(dto.id(), dto.campaignDto().id(), dto.campaignDto().clientUserDto().userId(), dto);

        // Then
        then(creativeRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("DELETE - 소재 ID, 캠페인 ID 입력 시 소재 삭제 - 정상 호출")
    @Test
    void givenCreativeId_whenDeletingCreative_thenDeletesCreative() {
        // Given
        Long creativeId = 1L;
        Long campaignId = 1L;
        String clientId = "t-client";
        willDoNothing().given(creativeRepository).setCreativeDeletedTrue(creativeId);

        // When
        sut.deleteCreative(creativeId, campaignId, clientId);

        // Then
        then(creativeRepository).should().setCreativeDeletedTrue(creativeId);
    }

    @DisplayName("READ - 캠페인 수를 조회할 경우 캠페인 수를 반환")
    @Test
    void givenNothing_whenCountingCreatives_thenReturnsCreativeCount() {
        // Given
        long expected = 0L;
        given(creativeRepository.countByDeletedFalse()).willReturn(expected);

        // When
        long actual = sut.getCreativeCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(creativeRepository).should().countByDeletedFalse();
    }
}
