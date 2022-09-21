package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.ClientUser;
import com.agencyplatformclonecoding.dto.CampaignDto;
import com.agencyplatformclonecoding.dto.CampaignWithCreativesDto;
import com.agencyplatformclonecoding.dto.response.CampaignResponse;
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
                .orElseThrow(() -> new EntityNotFoundException("캠페인이 존재하지 않습니다 - campaignId : " + campaignId));
    }

    @Transactional(readOnly = true)
    public CampaignWithCreativesDto getCampaignWithCreatives(Long campaignId) {
        return campaignRepository.findByIdAndDeletedFalse(campaignId)
                .map(CampaignWithCreativesDto::from)
                .orElseThrow(() -> new EntityNotFoundException("캠페인이 존재하지 않습니다 - campaignId : " + campaignId));
    }

    @Transactional(readOnly = true)
    public Page<CampaignDto> searchCampaigns(Pageable pageable, String clientId) {
        return campaignRepository.findByDeletedFalseAndClientUser_UserId(pageable, clientId).map(CampaignDto::from);
    }

    public void saveCampaign(CampaignDto dto) {
        ClientUser clientUser = clientUserRepository.getReferenceById(dto.clientUserDto().userId());
        campaignRepository.save(dto.toEntity(clientUser));
    }

    public void updateCampaign(Long campaignId, String clientId, CampaignDto dto) {

        try {
            validateClientAndCampaign(campaignId, clientId);
            Campaign campaign = campaignRepository.getReferenceById(campaignId);
            ClientUser clientUser = clientUserRepository.getReferenceById(dto.clientUserDto().userId());

            if (campaign.isDeleted()) {
                throw new EntityNotFoundException();
            }

            if (campaign.getClientUser().equals(clientUser))
                if (dto.name() != null) {
                    campaign.setName(dto.name());
                }
            campaign.setBudget(dto.budget());
        } catch (EntityNotFoundException e) {
            log.warn("캠페인을 수정하는데 필요한 정보를 찾을 수 없습니다. - dto : {}", e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            log.warn("캠페인-광고주가 매칭되어 있지 않습니다. 잘못된 경로로 접근하였습니다.", e.getLocalizedMessage());
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
            throw new IllegalArgumentException("캠페인-광고주가 매칭되어 있지 않습니다.");
        }
    }


}
