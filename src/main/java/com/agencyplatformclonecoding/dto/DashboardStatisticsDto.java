package com.agencyplatformclonecoding.dto;

import lombok.Getter;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Getter
public class DashboardStatisticsDto {

    Long agentGroupId;
    String agentGroupName;
    String agentId;
    String agentName;
    String clientId;
    String clientName;
    String category;
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
    Long minusVAT;
    String sMinusVAT;
    Long VAT;
    String sVAT;
    Long commission;
    String sCommission;
    LocalDate startDate;
    LocalDate lastDate;

    public DashboardStatisticsDto() {
    }

    public void setSpendIndicator(Long spend) {

        String sSpend = formatToString(spend);
        Long VAT = spend / 10;
        String sVAT = formatToString(VAT);
        Long minusVAT = spend - VAT;
        String sMinusVAT = formatToString(minusVAT);
        Long commission = minusVAT * 15 / 100;
        String sCommission = formatToString(commission);

        this.sSpend = sSpend;
        this.VAT = VAT;
        this.sVAT = sVAT;
        this.commission = commission;
        this.sCommission = sCommission;
        this.minusVAT = minusVAT;
        this.sMinusVAT = sMinusVAT;
    }

    public void setPerformanceIndicator(Long spend, Long view, Long click, Long conversion, Long purchase) {

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

    public void setStartDateAndLastDate(LocalDate startDate, LocalDate lastDate) {

        this.startDate = startDate;
        this.lastDate = lastDate;
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
