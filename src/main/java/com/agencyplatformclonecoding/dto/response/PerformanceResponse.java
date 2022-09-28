package com.agencyplatformclonecoding.dto.response;

import com.agencyplatformclonecoding.dto.PerformanceDto;

import java.io.Serializable;
import java.time.LocalDate;

public record PerformanceResponse(
        Long id,
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
        LocalDate createdAt,
        Long creativeId
) implements Serializable {

    public static PerformanceResponse of(Long id, Long view, String sView, Long click, String sClick, Long conversion, String sConversion, Long purchase, String sPurchase, Long spend, String sSpend, double CTR, String sCTR, double CVR, String sCVR, Long CPA, String sCPA, double ROAS, String sROAS, LocalDate createdAt, Long creativeId) {
        return new PerformanceResponse(id, view, sView, click, sClick, conversion, sConversion, purchase, sPurchase, spend, sSpend, CTR, sCTR, CVR, sCVR, CPA, sCPA, ROAS, sROAS, createdAt, creativeId);
    }

    public static PerformanceResponse from(PerformanceDto dto) {
        Long creativeId = dto.creativeId();

        return new PerformanceResponse(
                dto.id(),
                dto.view(),
                dto.sView(),
                dto.click(),
                dto.sClick(),
                dto.conversion(),
                dto.sConversion(),
                dto.purchase(),
                dto.sPurchase(),
                dto.spend(),
                dto.sSpend(),
                dto.CTR(),
                dto.sCTR(),
                dto.CVR(),
                dto.sCVR(),
                dto.CPA(),
                dto.sCPA(),
                dto.ROAS(),
                dto.sROAS(),
                dto.createdAt(),
                creativeId
        );
    }
}