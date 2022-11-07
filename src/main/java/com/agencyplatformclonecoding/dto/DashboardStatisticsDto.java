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
    Long spend;
    String sSpend;
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

    public void setStartDateAndLastDate(LocalDate startDate, LocalDate lastDate) {

        this.startDate = startDate;
        this.lastDate = lastDate;
    }

    public static String formatToString(Long amount) {
        if (amount != null) {
            DecimalFormat df = new DecimalFormat("###,###");
            return df.format(amount);
        }

        return "0";
    }

}
