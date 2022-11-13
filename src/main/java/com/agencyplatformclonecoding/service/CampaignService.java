package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.CampaignWithCreativesDto;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
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
public class CampaignService {

    private final ClientUserRepository clientUserRepository;
    private final CampaignRepository campaignRepository;

    @Transactional(readOnly = true)
    public CampaignDto getCampaign(Long campaignId) {
        return campaignRepository.findByIdAndDeletedFalse(campaignId)
                .map(CampaignDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.CAMPAIGN_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public CampaignWithCreativesDto getCampaignWithCreatives(Long campaignId) {
        return campaignRepository.findByIdAndDeletedFalse(campaignId)
                .map(CampaignWithCreativesDto::from)
                .orElseThrow(() -> new AdPlatformException(ErrorCode.CAMPAIGN_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<CampaignDto> searchCampaigns(Pageable pageable, String clientId) {
        return campaignRepository.findByDeletedFalseAndClientUser_UserId(pageable, clientId).map(CampaignDto::from);
    }

    public void saveCampaign(CampaignDto dto) {
        ClientUser clientUser = clientUserRepository.getReferenceById(dto.clientUserDto().userId());

        if (!isLong(dto.budget())) {
            throw new AdPlatformException(ErrorCode.BAD_REQUEST_ILLEGAL_ARGUMENT);
        }

        if (!isValidCampaignNameLength(dto.name())) {
            throw new AdPlatformException(ErrorCode.BAD_REQUEST_CAMPAIGN_NAME);
        }

        campaignRepository.save(dto.toEntity(clientUser));
    }

    public void updateCampaign(Long campaignId, String clientId, CampaignDto dto) {

        try {
            validateClientAndCampaign(campaignId, clientId);
            Campaign campaign = campaignRepository.getReferenceById(campaignId);
            ClientUser clientUser = clientUserRepository.getReferenceById(dto.clientUserDto().userId());

            if (campaign.isDeleted()) {
                throw new AdPlatformException(ErrorCode.BAD_REQUEST);
            }

            if (campaign.getClientUser().equals(clientUser))
                if (dto.name() != null) {
                    campaign.setName(dto.name());
                }

            if (!isValidCampaignNameLength(dto.name())) {
                throw new AdPlatformException(ErrorCode.BAD_REQUEST_CAMPAIGN_NAME);
            }

            if (!isLong(dto.budget())) {
                throw new AdPlatformException(ErrorCode.BAD_REQUEST_ILLEGAL_ARGUMENT);
            }
            campaign.setBudget(dto.budget());
        } catch (EntityNotFoundException e) {
            log.warn("캠페인을 수정하는데 필요한 정보를 찾을 수 없습니다. - dto : {}", e.getLocalizedMessage());
        }
    }

    public void deleteCampaign(Long campaignId, String clientId) {
        campaignRepository.setCampaignDeletedTrue(campaignId);
    }

    public void toggleCampaignActivate(Long campaignId, String clientId) {

        Campaign campaign = campaignRepository.getReferenceById(campaignId);

        if (campaign.isActivated()) {
            campaignRepository.setCampaignDisabled(campaignId);
        }
        if (!campaign.isActivated()) {
            campaignRepository.setCampaignActivated(campaignId);
        }
    }

    public long getCampaignCount() {
        return campaignRepository.countByDeletedFalse();
    }

    public void validateClientAndCampaign(Long campaignId, String clientId) {

        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        ClientUser clientUser = clientUserRepository.getReferenceById(clientId);

        if (!campaign.getClientUser().equals(clientUser)) {
            throw new AdPlatformException(ErrorCode.INVALID_RELATION);
        }
    }

    public boolean isLong(Object object) {
        return object instanceof Long;
    }

    public boolean isValidCampaignNameLength(String string) {
        return string.length() <= 20;
    }


}
