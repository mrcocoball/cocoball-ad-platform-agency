package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.dto.CreativeDto;
import com.agencyplatformclonecoding.dto.PerformanceDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.agencyplatformclonecoding.domain.QCampaign.campaign;
import static com.agencyplatformclonecoding.domain.QCreative.creative;
import static com.agencyplatformclonecoding.domain.QPerformance.performance;

@Repository
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<PerformanceStatisticsDto> findByCreative_IdAndStatisticsDefault(@Param("id") Long creativeId,
                                                                                @Param("startDate") LocalDate startDate,
                                                                                @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        performance.creative.id,
                        performance.view,
                        performance.click,
                        performance.conversion,
                        performance.purchase,
                        performance.spend
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        performance.creative.id.eq(creativeId)
                )
                .groupBy(performance.creative.id)
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();
            Long view = result.getView();
            Long click = result.getClick();
            Long conversion = result.getConversion();
            Long purchase = result.getPurchase();

            result.setCreativeIndicator(spend, view, click, conversion, purchase);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findByCampaign_IdAndStatisticsDefault(@Param("id") Long campaignId,
                                                                                @Param("startDate") LocalDate startDate,
                                                                                @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        performance.creative.id.as("creativeId"),
                        performance.creative.keyword.as("keyword"),
                        performance.creative.bidingPrice.as("bidingPrice"),
                        performance.creative.deleted.as("deleted"),
                        performance.creative.activated.as("activated"),
                        performance.view.sum().as("view"),
                        performance.click.sum().as("click"),
                        performance.conversion.sum().as("conversion"),
                        performance.purchase.sum().as("purchase"),
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        campaign.id.eq(campaignId),
                        performance.creative.deleted.eq(false)
                )
                .groupBy(performance.creative.id)
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();
            Long view = result.getView();
            Long click = result.getClick();
            Long conversion = result.getConversion();
            Long purchase = result.getPurchase();

            result.setCreativeIndicator(spend, view, click, conversion, purchase);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findByCampaign_IdAndTotalStatisticsDefault(@Param("id") Long campaignId,
                                                                                @Param("startDate") LocalDate startDate,
                                                                                @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        performance.view.sum().as("view"),
                        performance.click.sum().as("click"),
                        performance.conversion.sum().as("conversion"),
                        performance.purchase.sum().as("purchase"),
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        campaign.id.eq(campaignId),
                        performance.creative.deleted.eq(false)
                )
                .groupBy(performance.creative.campaign.id)
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();
            Long view = result.getView();
            Long click = result.getClick();
            Long conversion = result.getConversion();
            Long purchase = result.getPurchase();

            result.setCampaignTotalIndicator(spend, view, click, conversion, purchase);
        }

        return results;
    }

    private StringTemplate localDateTimeFormat() {
        return Expressions
                .stringTemplate("DATE_FORMAT({0}, {1})", performance.createdAt, ConstantImpl.create("%Y-%m-%d"));
    }
}
