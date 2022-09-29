package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.PerformanceDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;

import java.io.Serializable;
import java.time.LocalDate;

public record PerformanceStatisticsResponse(
        Long creativeId,
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
        String sROAS

) implements Serializable {

    public static PerformanceStatisticsResponse of(Long creativeId, Long view, String sView, Long click, String sClick, Long conversion, String sConversion, Long purchase, String sPurchase, Long spend, String sSpend, double CTR, String sCTR, double CVR, String sCVR, Long CPA, String sCPA, double ROAS, String sROAS) {
        return new PerformanceStatisticsResponse(creativeId, view, sView, click, sClick, conversion, sConversion, purchase, sPurchase, spend, sSpend, CTR, sCTR, CVR, sCVR, CPA, sCPA, ROAS, sROAS);
    }

    public static PerformanceStatisticsResponse from(PerformanceStatisticsDto dto) {

        return new PerformanceStatisticsResponse(
                dto.getCreativeId(),
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
                dto.getSROAS()
        );
    }
}
