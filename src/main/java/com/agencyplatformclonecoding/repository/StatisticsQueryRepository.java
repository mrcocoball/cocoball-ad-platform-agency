package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.agencyplatformclonecoding.domain.QCampaign.campaign;
import static com.agencyplatformclonecoding.domain.QClientUser.clientUser;
import static com.agencyplatformclonecoding.domain.QCreative.creative;
import static com.agencyplatformclonecoding.domain.QPerformance.performance;

@Repository
public class StatisticsQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public StatisticsQueryRepository(JPAQueryFactory jpaQueryFactory) {
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

            result.setTotalIndicator(spend, view, click, conversion, purchase);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findByClientUser_IdAndStatisticsDefault(@Param("id") String clientId,
                                                                                  @Param("startDate") LocalDate startDate,
                                                                                  @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        campaign.id.as("campaignId"),
                        campaign.name.as("name"),
                        campaign.budget.as("budget"),
                        campaign.deleted.as("deleted"),
                        performance.view.sum().as("view"),
                        performance.click.sum().as("click"),
                        performance.conversion.sum().as("conversion"),
                        performance.purchase.sum().as("purchase"),
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .leftJoin(campaign.clientUser, clientUser)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        clientUser.userId.eq(clientId),
                        campaign.deleted.eq(false)
                )
                .groupBy(campaign.id)
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();
            Long view = result.getView();
            Long click = result.getClick();
            Long conversion = result.getConversion();
            Long purchase = result.getPurchase();

            result.setCampaignIndicator(spend, view, click, conversion, purchase);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findByClientUser_IdAndTotalStatisticsDefault(@Param("id") String clientId,
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
                .leftJoin(campaign.clientUser, clientUser)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        clientUser.userId.eq(clientId),
                        campaign.deleted.eq(false)
                )
                .groupBy(clientUser.userId)
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();
            Long view = result.getView();
            Long click = result.getClick();
            Long conversion = result.getConversion();
            Long purchase = result.getPurchase();

            result.setTotalIndicator(spend, view, click, conversion, purchase);
        }

        return results;
    }

    private StringTemplate localDateTimeFormat() {
        return Expressions
                .stringTemplate("DATE_FORMAT({0}, {1})", performance.createdAt, ConstantImpl.create("%Y-%m-%d"));
    }
}
