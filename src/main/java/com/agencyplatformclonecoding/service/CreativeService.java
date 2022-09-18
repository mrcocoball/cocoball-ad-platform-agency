package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.Campaign;
import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.dto.CreativeDto;
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

import static com.agencyplatformclonecoding.domain.QCampaign.campaign;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CreativeService {

    private final CampaignRepository campaignRepository;
    private final CreativeRepository creativeRepository;

    @Transactional(readOnly = true)
    public CreativeDto getCreative(Long creativeId) {
        return creativeRepository.findByIdAndDeletedFalse(creativeId)
                .map(CreativeDto::from)
                .orElseThrow(() -> new EntityNotFoundException("소재가 존재하지 않습니다 - creativeId : " + creativeId));
    }

    @Transactional(readOnly = true)
    public Page<CreativeDto> searchCreatives(Pageable pageable) {
        return creativeRepository.findByDeletedFalse(pageable).map(CreativeDto::from);
    }

    public void saveCreative(CreativeDto dto) {
        try {
            Campaign campaign = campaignRepository.getReferenceById(dto.campaignDto().id());
            creativeRepository.save(dto.toEntity(campaign));
        } catch (EntityNotFoundException e) {
            log.warn("소재 저장에 실패하였습니다. 소재 저장에 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    public void updateCreative(Long creativeId, CreativeDto dto) {

        try {
            Creative creative = creativeRepository.getReferenceById(creativeId);
            Campaign campaign = campaignRepository.getReferenceById(dto.campaignDto().id());

            if (creative.isDeleted()) {
                throw new EntityNotFoundException();
            }

            if (creative.getCampaign().equals(campaign))
                if (dto.keyword() != null) {
                    creative.setKeyword(dto.keyword());
                }
            creative.setBidingPrice(dto.bidingPrice());
        } catch (EntityNotFoundException e) {
            log.warn("소재를 수정하는데 필요한 정보를 찾을 수 없습니다. - dto : {}", e.getLocalizedMessage());
        }
    }

    public void deleteCreative(Long creativeId) {
        creativeRepository.setCreativeDeletedTrue(creativeId);
    }

    public long getCreativeCount() {
        return creativeRepository.countByDeletedFalse();
    }

}
