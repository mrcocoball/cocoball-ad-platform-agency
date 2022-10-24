package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.PerformanceDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;

import java.io.Serializable;
import java.time.LocalDate;

public record PerformanceStatisticsResponse(
        String clientId,
        Long campaignId,
        Long creativeId,
        String username,
        String name,
        String keyword,
        Long budget,
        String sBudget,
        Long bidingPrice,
        String sBidingPrice,
        Long view,
        String sView,
        Long click,
        String sClick,
        Long conversion,
        String sConversion,
        Long purchase,
        String sPurchase,
        Long spend,
        String sSpend,
        double CTR,
        String sCTR,
        double CVR,
        String sCVR,
        Long CPA,
        String sCPA,
        double ROAS,
        String sROAS,
        boolean activated,
        boolean deleted

) implements Serializable {

    public static PerformanceStatisticsResponse of(String clientId, Long campaignId, Long creativeId, String username, String name, String keyword, Long budget, String sBudget, Long bidingPrice, String sBidingPrice, Long view, String sView, Long click, String sClick, Long conversion, String sConversion, Long purchase, String sPurchase, Long spend, String sSpend, double CTR, String sCTR, double CVR, String sCVR, Long CPA, String sCPA, double ROAS, String sROAS, boolean activated, boolean deleted) {
        return new PerformanceStatisticsResponse(clientId, campaignId, creativeId, username, name, keyword, budget, sBudget, bidingPrice, sBidingPrice, view, sView, click, sClick, conversion, sConversion, purchase, sPurchase, spend, sSpend, CTR, sCTR, CVR, sCVR, CPA, sCPA, ROAS, sROAS, activated, deleted);
    }

    public static PerformanceStatisticsResponse from(PerformanceStatisticsDto dto) {

        return new PerformanceStatisticsResponse(
                dto.getClientId(),
                dto.getCampaignId(),
                dto.getCreativeId(),
                dto.getUsername(),
                dto.getName(),
                dto.getKeyword(),
                dto.getBudget(),
                dto.getSBudget(),
                dto.getBidingPrice(),
                dto.getSBidingPrice(),
                dto.getView(),
                dto.getSView(),
                dto.getClick(),
                dto.getSClick(),
                dto.getConversion(),
                dto.getSConversion(),
                dto.getPurchase(),
                dto.getSPurchase(),
                dto.getSpend(),
                dto.getSSpend(),
                dto.getCTR(),
                dto.getSCTR(),
                dto.getCVR(),
                dto.getSCVR(),
                dto.getCPA(),
                dto.getSCPA(),
                dto.getROAS(),
                dto.getSROAS(),
                dto.isActivated(),
                dto.isDeleted()
        );
    }
}
