package com.agencyplatformclonecoding.dto;

import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class PerformanceStatisticsDto {
    String clientId;
    Long campaignId;
    Long creativeId;
    String username;
    String name;
    String keyword;
    Long budget;
    String sBudget;
    Long bidingPrice;
    String sBidingPrice;
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
    boolean activated;
    boolean deleted;

    public PerformanceStatisticsDto() {
    }

    public void setCreativeIndicator(Long spend, Long view, Long click, Long conversion, Long purchase) {

        String sBidingPrice = formatToString(bidingPrice);
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

        this.sSpend = sSpend;
        this.sBidingPrice = sBidingPrice;
        this.sView = sView;
        this.sClick = sClick;
        this.sConversion = sConversion;
        this.sPurchase = sPurchase;
        this.CTR = CTR;
        this.sCTR = sCTR;
        this.CVR = CVR;
        this.sCVR = sCVR;
        this.CPA = CPA;
        this.sCPA = sCPA;
        this.ROAS = ROAS;
        this.sROAS = sROAS;
    }

    public void setCampaignIndicator(Long spend, Long view, Long click, Long conversion, Long purchase) {

        String sBudget = formatToString(budget);
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

        this.sSpend = sSpend;
        this.sBudget = sBudget;
        this.sView = sView;
        this.sClick = sClick;
        this.sConversion = sConversion;
        this.sPurchase = sPurchase;
        this.CTR = CTR;
        this.sCTR = sCTR;
        this.CVR = CVR;
        this.sCVR = sCVR;
        this.CPA = CPA;
        this.sCPA = sCPA;
        this.ROAS = ROAS;
        this.sROAS = sROAS;
    }

    public void setTotalIndicator(Long spend, Long view, Long click, Long conversion, Long purchase) {

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

        this.sSpend = sSpend;
        this.sView = sView;
        this.sClick = sClick;
        this.sConversion = sConversion;
        this.sPurchase = sPurchase;
        this.CTR = CTR;
        this.sCTR = sCTR;
        this.CVR = CVR;
        this.sCVR = sCVR;
        this.CPA = CPA;
        this.sCPA = sCPA;
        this.ROAS = ROAS;
        this.sROAS = sROAS;
    }

    public static double calculateCTR(Long click, Long view) {
        if (view == 0 || view == null) {
            return 0;
        }
        return (double) click / view;
    }

    public static double calculateCVR(Long conversion, Long click) {
        if (click == 0 || click == null) {
            return 0;
        }
        return (double) conversion / click;
    }

    public static Long calculateCPA(Long spend, Long conversion) {
        if (conversion == 0 || conversion == null) {
            return null;
        }
        return spend / conversion;
    }

    public static double calculateROAS(Long purchase, Long spend) {
        if (spend == 0 || spend == null) {
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
