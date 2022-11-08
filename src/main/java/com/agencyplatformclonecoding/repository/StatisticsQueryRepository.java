package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.dto.DashboardStatisticsDto;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.agencyplatformclonecoding.domain.QAgent.agent;
import static com.agencyplatformclonecoding.domain.QAgentGroup.agentGroup;
import static com.agencyplatformclonecoding.domain.QCampaign.campaign;
import static com.agencyplatformclonecoding.domain.QCategory.category;
import static com.agencyplatformclonecoding.domain.QClientUser.clientUser;
import static com.agencyplatformclonecoding.domain.QCreative.creative;
import static com.agencyplatformclonecoding.domain.QPerformance.performance;

@Repository
public class StatisticsQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public StatisticsQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<PerformanceStatisticsDto> findPerformanceStatisticsByCreativeId(@Param("id") Long creativeId,
                                                                                @Param("startDate") LocalDate startDate,
                                                                                @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        performance.creative.id.as("creativeId"),
                        creative.bidingPrice.as("bidingPrice"),
                        creative.keyword.as("keyword"),
                        performance.view.as("view"),
                        performance.click.as("click"),
                        performance.conversion.as("conversion"),
                        performance.purchase.as("purchase"),
                        performance.spend.as("spend"),
                        performance.createdAt.as("startDate")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        performance.creative.id.eq(creativeId)
                )
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

    public List<PerformanceStatisticsDto> findTotalPerformanceStatisticsByCreativeId(@Param("id") Long creativeId,
                                                                                     @Param("startDate") LocalDate startDate,
                                                                                     @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        performance.creative.id,
                        performance.view.sum().as("view"),
                        performance.click.sum().as("click"),
                        performance.conversion.sum().as("conversion"),
                        performance.purchase.sum().as("purchase"),
                        performance.spend.sum().as("spend")
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
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findCreativeStatisticsByCampaignId(@Param("id") Long campaignId,
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
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findTotalCreativeStatisticsByCampaignId(@Param("id") Long campaignId,
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
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findCampaignStatisticsByClientId(@Param("id") String clientId,
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
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findTotalCampaignStatisticsByClientId(@Param("id") String clientId,
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
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    public List<PerformanceStatisticsDto> findTotalSpendStatistics(@Param("startDate") LocalDate startDate,
                                                                   @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    // 광고주별 실적 가져오기
    public List<PerformanceStatisticsDto> findClientUserPerformanceStatistics(@Param("startDate") LocalDate startDate,
                                                                              @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        clientUser.userId.as("clientId"),
                        clientUser.nickname.as("username"),
                        category.name.as("category"),
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
                .leftJoin(clientUser.category, category)
                .where(
                        performance.createdAt.between(startDate, lastDate),
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
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    // 광고주별 소진액 가져오기
    public List<PerformanceStatisticsDto> findClientUserSpendStatistics(@Param("startDate") LocalDate startDate,
                                                                        @Param("lastDate") LocalDate lastDate
    ) {
        List<PerformanceStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(PerformanceStatisticsDto.class,
                        clientUser.userId.as("clientId"),
                        clientUser.nickname.as("username"),
                        category.name.as("category"),
                        agent.userId.as("agentId"),
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .leftJoin(campaign.clientUser, clientUser)
                .leftJoin(clientUser.agent, agent)
                .leftJoin(clientUser.category, category)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .groupBy(clientUser.userId)
                .fetch();

        for (PerformanceStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        return results;
    }

    public List<DashboardStatisticsDto> dashboardTestQuery(@Param("startDate") LocalDate startDate,
                                                           @Param("lastDate") LocalDate lastDate
    ) {
        List<DashboardStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(DashboardStatisticsDto.class,
                        performance.spend.sum().as("spend"),
                        performance.createdAt.as("startDate")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .groupBy(performance.createdAt)
                .fetch();

        for (DashboardStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
        }

        return results;
    }

    public List<DashboardStatisticsDto> dashboardTestQuery2(@Param("startDate") LocalDate startDate,
                                                            @Param("lastDate") LocalDate lastDate
    ) {
        List<DashboardStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(DashboardStatisticsDto.class,
                        performance.spend.sum().as("spend"),
                        performance.createdAt.as("startDate")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .groupBy(performance.createdAt)
                .orderBy(performance.createdAt.desc())
                .fetch();

        for (DashboardStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
        }

        return results;
    }

    // 광고주별 소진액 차트 출력
    public List<DashboardStatisticsDto> dashboardTestQuery3(@Param("startDate") LocalDate startDate,
                                                            @Param("lastDate") LocalDate lastDate
    ) {
        List<DashboardStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(DashboardStatisticsDto.class,
                        clientUser.userId.as("clientId"),
                        clientUser.nickname.as("clientName"),
                        performance.spend.sum().as("spend"),
                        category.name.as("category")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .leftJoin(campaign.clientUser, clientUser)
                .leftJoin(clientUser.category, category)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .groupBy(clientUser.userId)
                .fetch();

        for (DashboardStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        results = results.stream()
                .sorted(Comparator.comparing(DashboardStatisticsDto::getSpend).reversed())
                .collect(Collectors.toList()); // Collections의 comparing을 사용, 내림차순 정렬

        return results;
    }

    // 에이전트 소진액 차트 출력
    public List<DashboardStatisticsDto> dashboardTestQuery4(@Param("startDate") LocalDate startDate,
                                                            @Param("lastDate") LocalDate lastDate
    ) {
        List<DashboardStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(DashboardStatisticsDto.class,
                        agent.userId.as("agentId"),
                        agent.nickname.as("agentName"),
                        agentGroup.name.as("agentGroupName"),
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .leftJoin(campaign.clientUser, clientUser)
                .leftJoin(clientUser.agent, agent)
                .leftJoin(agent.agentGroup, agentGroup)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .groupBy(agent.userId)
                .fetch();

        for (DashboardStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        results = results.stream()
                .sorted(Comparator.comparing(DashboardStatisticsDto::getSpend).reversed())
                .collect(Collectors.toList()); // Collections의 comparing을 사용, 내림차순 정렬

        return results;
    }

    // 에이전트 그룹 소진액 차트 출력
    public List<DashboardStatisticsDto> dashboardTestQuery5(@Param("startDate") LocalDate startDate,
                                                            @Param("lastDate") LocalDate lastDate
    ) {
        List<DashboardStatisticsDto> results = jpaQueryFactory
                .select(Projections.fields(DashboardStatisticsDto.class,
                        agentGroup.name.as("agentGroupName"),
                        performance.spend.sum().as("spend")
                ))
                .from(performance)
                .leftJoin(performance.creative, creative)
                .leftJoin(creative.campaign, campaign)
                .leftJoin(campaign.clientUser, clientUser)
                .leftJoin(clientUser.agent, agent)
                .leftJoin(agent.agentGroup, agentGroup)
                .where(
                        performance.createdAt.between(startDate, lastDate),
                        creative.deleted.eq(false)
                )
                .groupBy(agentGroup.id)
                .fetch();

        for (DashboardStatisticsDto result : results) {
            Long spend = result.getSpend();

            result.setSpendIndicator(spend);
            result.setStartDateAndLastDate(startDate, lastDate);
        }

        results = results.stream()
                .sorted(Comparator.comparing(DashboardStatisticsDto::getSpend).reversed())
                .collect(Collectors.toList()); // Collections의 comparing을 사용, 내림차순 정렬

        return results;
    }

    private StringTemplate localDateTimeFormat() {
        return Expressions
                .stringTemplate("DATE_FORMAT({0}, {1})", performance.createdAt, ConstantImpl.create("%Y-%m-%d"));
    }
}
