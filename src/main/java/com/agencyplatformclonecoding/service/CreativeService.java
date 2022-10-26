package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.dto.CreativeDto;
import com.agencyplatformclonecoding.dto.CreativeWithPerformancesDto;
import com.agencyplatformclonecoding.exception.AdPlatformException;
import com.agencyplatformclonecoding.exception.ErrorCode;
import com.agencyplatformclonecoding.repository.CampaignRepository;
import com.agencyplatformclonecoding.repository.ClientUserRepository;
import com.agencyplatformclonecoding.repository.CreativeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CreativeService {

    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;
    private final ClientUserRepository clientUserRepository;

    @Transactional(readOnly = true)
    public CreativeDto getCreative(Long creativeId) {
        return creativeRepository.findByIdAndDeletedFalse(creativeId)
                .map(CreativeDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.CREATIVE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<CreativeDto> searchCreatives(Pageable pageable, Long campaignId, String clientId) {
        validateClientAndCampaign(campaignId, clientId);
        return creativeRepository.findByDeletedFalseAndCampaign_Id(pageable, campaignId).map(CreativeDto::from);
    }

    public void saveCreative(CreativeDto dto) {
        try {
            Campaign campaign = campaignRepository.getReferenceById(dto.campaignDto().id());
            creativeRepository.save(dto.toEntity(campaign));
        } catch (EntityNotFoundException e) {
            log.warn("소재 저장에 실패하였습니다. 소재 저장에 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    public void updateCreative(Long creativeId, Long campaignId, String clientId, CreativeDto dto) {

        try {
            validateClientAndCampaignAndCreative(creativeId, campaignId, clientId);
            Creative creative = creativeRepository.getReferenceById(creativeId);
            Campaign campaign = campaignRepository.getReferenceById(dto.campaignDto().id());

            if (creative.isDeleted()) {
                throw new AdPlatformException(ErrorCode.BAD_REQUEST);
            }

            if (creative.getCampaign().equals(campaign))
                if (dto.keyword() != null) {
                    creative.setKeyword(dto.keyword());
                }
            if (dto.description() != null) {
                creative.setDescription(dto.description());

            }
            if (dto.url() != null) {
                creative.setUrl(dto.url());
            }

            creative.setBidingPrice(dto.bidingPrice());
        } catch (EntityNotFoundException e) {
            log.warn("소재를 수정하는데 필요한 정보를 찾을 수 없습니다. - dto : {}", e.getLocalizedMessage());
        }
    }

    public void deleteCreative(Long creativeId, Long campaignId, String clientId) {
        creativeRepository.setCreativeDeletedTrue(creativeId);
    }

    public void toggleCreativeActivate(Long creativeId, Long campaignId, String clientId) {

        Creative creative = creativeRepository.getReferenceById(creativeId);

        if (creative.isActivated()) {
            creativeRepository.setCreativeDisabled(creativeId);
        }
        if (!creative.isActivated()) {
            creativeRepository.setCreativeActivated(creativeId);
        }
    }

    public long getCreativeCount() {
        return creativeRepository.countByDeletedFalse();
    }

    public void validateClientAndCampaign(Long campaignId, String clientId) {

        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        ClientUser clientUser = clientUserRepository.getReferenceById(clientId);

        if (campaign == null) {
            throw new AdPlatformException(ErrorCode.CAMPAIGN_NOT_FOUND);
        }

        if (clientUser == null) {
            throw new AdPlatformException(ErrorCode.CLIENT_NOT_FOUND);
        }

        if (!campaign.getClientUser().equals(clientUser)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }
    }

    @Transactional(readOnly = true)
    public CreativeWithPerformancesDto getCreativeWithPerformances(Long creativeId) {
        return creativeRepository.findByIdAndDeletedFalse(creativeId)
                .map(CreativeWithPerformancesDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.CREATIVE_NOT_FOUND));
    }

    public void validateClientAndCampaignAndCreative(Long creativeId, Long campaignId, String clientId) {

        Creative creative = creativeRepository.getReferenceById(creativeId);
        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        ClientUser clientUser = clientUserRepository.getReferenceById(clientId);

        if (creative == null) {
            throw new AdPlatformException(ErrorCode.CREATIVE_NOT_FOUND);
        }

        if (campaign == null) {
            throw new AdPlatformException(ErrorCode.CAMPAIGN_NOT_FOUND);
        }

        if (clientUser == null) {
            throw new AdPlatformException(ErrorCode.CLIENT_NOT_FOUND);
        }

        if (!campaign.getClientUser().equals(clientUser)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }

        if (!creative.getCampaign().equals(campaign)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }
    }

}
