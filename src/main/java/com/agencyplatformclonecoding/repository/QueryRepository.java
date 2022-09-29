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
                .join(creative).on(performance.creative.id.eq(creativeId))
                .where(
                        performance.createdAt.between(startDate, lastDate)
                )
                .groupBy(performance.createdAt)
                .orderBy(performance.createdAt.desc())
                .fetch();

        Long spend = 0L;
        Long view = 0L;
        Long click = 0L;
        Long conversion = 0L;
        Long purchase = 0L;

        for (PerformanceStatisticsDto result : results) {
            if (result.getSpend() != null) spend += result.getSpend();
            if (result.getView() != null) view += result.getView();
            if (result.getClick() != null) click += result.getClick();
            if (result.getPurchase() != null) purchase += result.getPurchase();
            if (result.getConversion() != null) conversion += result.getConversion();
        }

        PerformanceStatisticsDto totalResult = PerformanceStatisticsDto.of(creativeId, view, click, conversion, purchase, spend);
        results.add(0, totalResult);
        return results;
    }

    private StringTemplate localDateTimeFormat() {
        return Expressions
                .stringTemplate("DATE_FORMAT({0}, {1})", performance.createdAt, ConstantImpl.create("%Y-%m-%d"));
    }
}
