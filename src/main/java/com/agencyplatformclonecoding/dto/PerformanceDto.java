package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Creative;
import com.agencyplatformclonecoding.domain.Performance;

import java.time.LocalDate;

public record PerformanceDto(
        CreativeDto creativeDto,
		Long creativeId,
        Long id,
        Long view,
        Long click,
        Long conversion,
        Long purchase,
		Long spend,
		double CTR,
        String sCTR,
		double CVR,
        String sCVR,
		Long CPA,
		double ROAS,
        String sROAS,
        LocalDate createdAt
) {

    public static PerformanceDto of(CreativeDto creativeDto, Long creativeId, Long view, Long click, Long conversion, Long purchase) {
        return new PerformanceDto(creativeDto, creativeId, null, view, click, conversion, purchase, null, 0, null, 0, null, 0L, 0, null, null);
    }

    public static PerformanceDto of(CreativeDto creativeDto, Long creativeId, Long id, Long view, Long click, Long conversion, Long purchase, LocalDate createdAt) {

        Long spend = creativeDto.bidingPrice() * click;
        double CTR = calculateCTR(click, view);
        String sCTR = String.format("%.2f", CTR);
        double CVR = calculateCVR(conversion, click);
        String sCVR = String.format("%.2f", CVR);
        Long CPA = calculateCPA(spend, conversion);
        double ROAS = calculateROAS(purchase, spend);
        String sROAS = String.format("%.2f", ROAS);

		return new PerformanceDto(creativeDto, creativeId, id, view, click, conversion, purchase, spend, CTR, sCTR, CVR, sCVR, CPA, ROAS, sROAS, createdAt);
    }

	public static PerformanceDto of(CreativeDto creativeDto, Long creativeId, Long id, Long view, Long click, Long conversion, Long purchase, Long spend, double CTR, String sCTR, double CVR, String sCVR, Long CPA, double ROAS, String sROAS, LocalDate createdAt) {

		return new PerformanceDto(creativeDto, creativeId, id, view, click, conversion, purchase, spend, CTR, sCTR, CVR, sCVR, CPA, ROAS, sROAS, createdAt);
    }

    // Entity -> dto로 변환
    public static PerformanceDto from(Performance entity) {

		Long spend = entity.getCreative().getBidingPrice() * entity.getClick();
        double CTR = calculateCTR(entity.getClick(),entity.getView());
        String sCTR = String.format("%.2f", CTR*100);
        double CVR = calculateCVR(entity.getConversion(), entity.getClick());
        String sCVR = String.format("%.2f", CVR*100);
        Long CPA = calculateCPA(spend, entity.getConversion());
        double ROAS = calculateROAS(entity.getPurchase(), spend);
        String sROAS = String.format("%.2f", ROAS*100);

        return new PerformanceDto(
                CreativeDto.from(entity.getCreative()),
                entity.getCreative().getId(),
                entity.getId(),
                entity.getView(),
                entity.getClick(),
                entity.getConversion(),
				entity.getPurchase(),
				spend,
				CTR,
                sCTR,
				CVR,
                sCVR,
				CPA,
				ROAS,
                sROAS,
                entity.getCreatedAt()
        );
    }

    // dto -> Entity로 변환
    public Performance toEntity(Creative creative) {
        return Performance.of(
                creative,
				view,
				click,
				conversion,
				purchase
        );
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
        } return (double) conversion / click;
    }

    public static Long calculateCPA(Long spend, Long conversion) {
        if (conversion == 0) {
            return null;
        } return spend / conversion;
    }

    public static double calculateROAS(Long purchase, Long spend) {
        if (spend == 0) {
            return 0;
        } return (double) purchase / spend;
    }
}