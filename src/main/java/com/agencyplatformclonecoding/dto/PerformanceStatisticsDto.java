package com.agencyplatformclonecoding.dto;

import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class PerformanceStatisticsDto {
    Long creativeId;
    Long view;
    String sView;
    Long click;
    String sClick;
    Long conversion;
    String sConversion;
    Long purchase;
    String sPurchase;
    Long spend;
    String sSpend;
    double CTR;
    String sCTR;
    double CVR;
    String sCVR;
    Long CPA;
    String sCPA;
    double ROAS;
    String sROAS;

    public PerformanceStatisticsDto() {
    }

    private PerformanceStatisticsDto(Long creativeId, Long view, String sView, Long click, String sClick, Long conversion, String sConversion, Long purchase, String sPurchase, Long spend, String sSpend, double CTR, String sCTR, double CVR, String sCVR, Long CPA, String sCPA, double ROAS, String sROAS) {
        this.creativeId = creativeId;
        this.view = view;
        this.sView = sView;
        this.click = click;
        this.sClick = sClick;
        this.conversion = conversion;
        this.sConversion = sConversion;
        this.purchase = purchase;
        this.sPurchase = sPurchase;
        this.spend = spend;
        this.sSpend = sSpend;
        this.CTR = CTR;
        this.sCTR = sCTR;
        this.CVR = CVR;
        this.sCVR = sCVR;
        this.CPA = CPA;
        this.sCPA = sCPA;
        this.ROAS = ROAS;
        this.sROAS = sROAS;
    }

    public static PerformanceStatisticsDto of(Long creativeId, Long view, Long click, Long conversion, Long purchase, Long spend) {

        String sSpend = formatToString(spend);
        String sView = formatToString(view);
        String sClick = formatToString(click);
        String sConversion = formatToString(conversion);
        String sPurchase = formatToString(purchase);
        double CTR = calculateCTR(click, view);
        String sCTR = String.format("%.2f", CTR * 100);
        double CVR = calculateCVR(conversion, click);
        String sCVR = String.format("%.2f", CVR * 100);
        Long CPA = calculateCPA(spend, conversion);
        String sCPA = formatToString(CPA);
        double ROAS = calculateROAS(purchase, spend);
        String sROAS = String.format("%.2f", ROAS * 100);

        return new PerformanceStatisticsDto(creativeId, view, sView, click, sClick, conversion, sConversion, purchase, sPurchase, spend, sSpend, CTR, sCTR, CVR, sCVR, CPA, sCPA, ROAS, sROAS);
    }

    public static PerformanceStatisticsDto of(Long creativeId, Long view, String sView, Long click, String sClick, Long conversion, String sConversion, Long purchase, String sPurchase, Long spend, String sSpend, double CTR, String sCTR, double CVR, String sCVR, Long CPA, String sCPA, double ROAS, String sROAS) {

        return new PerformanceStatisticsDto(creativeId, view, sView, click, sClick, conversion, sConversion, purchase, sPurchase, spend, sSpend, CTR, sCTR, CVR, sCVR, CPA, sCPA, ROAS, sROAS);
    }

    public static double calculateCTR(Long click, Long view) {
        if (view == 0) {
            return 0;
        }
        return (double) click / view;
    }

    public static double calculateCVR(Long conversion, Long click) {
        if (click == 0) {
            return 0;
        }
        return (double) conversion / click;
    }

    public static Long calculateCPA(Long spend, Long conversion) {
        if (conversion == 0) {
            return null;
        }
        return spend / conversion;
    }

    public static double calculateROAS(Long purchase, Long spend) {
        if (spend == 0) {
            return 0;
        }
        return (double) purchase / spend;
    }

    public static String formatToString(Long amount) {
        if (amount != null) {
            DecimalFormat df = new DecimalFormat("###,###");
            return df.format(amount);
        }

        return "0";
    }
}
