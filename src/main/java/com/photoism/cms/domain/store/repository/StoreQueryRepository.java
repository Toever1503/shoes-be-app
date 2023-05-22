package com.photoism.cms.domain.store.repository;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.store.dto.StoreDetailResDto;
import com.photoism.cms.domain.store.dto.StoreResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.photoism.cms.domain.etc.entity.QCodeEntity.codeEntity;
import static com.photoism.cms.domain.store.entity.QStoreEntity.storeEntity;

@Repository
@RequiredArgsConstructor
public class StoreQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<StoreResDto> findStoreList(String brandCd, String storeTypeCd, String countryCd, String cityCd, String name, Pageable pageable, List<Long> storeList) {
        BooleanBuilder builder = new BooleanBuilder();
        if (brandCd != null)        builder.and(storeEntity.brandCd.eq(brandCd));
        if (storeTypeCd != null)    builder.and(storeEntity.storeTypeCd.eq(storeTypeCd));
        if (countryCd != null)      builder.and(storeEntity.countryCd.eq(countryCd));
        if (cityCd != null)         builder.and(storeEntity.cityCd.eq(cityCd));
        if (name != null)           builder.and(storeEntity.name.contains(name));
        if (countryCd != null)      builder.and(storeEntity.countryCd.eq(countryCd));
        if (storeList != null)      builder.and(storeEntity.id.in(storeList));
        builder.and(storeEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<StoreResDto> content = jpaQueryFactory
                .select(Projections.constructor(StoreResDto.class,
                        storeEntity.id,
                        storeEntity.brandCd.as("brandCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.brandCd)), "brandNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.brandCd)), "brandNmEn"),
                        storeEntity.storeTypeCd.as("storeTypeCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.storeTypeCd)), "storeTypeNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.storeTypeCd)), "storeTypeNmEn"),
                        storeEntity.countryCd.as("countryCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.countryCd)), "countryNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.countryCd)), "countryNmEn"),
                        storeEntity.cityCd.as("cityCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.cityCd)), "cityNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.cityCd)), "cityNmEn"),
                        storeEntity.name,
                        storeEntity.createDate))
                .from(storeEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public Optional<StoreDetailResDto> getDetail(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(StoreDetailResDto.class,
                        storeEntity.id,
                        storeEntity.brandCd.as("brandCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.brandCd)), "brandNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.brandCd)), "brandNmEn"),
                        storeEntity.storeTypeCd.as("storeTypeCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.storeTypeCd)), "storeTypeNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.storeTypeCd)), "storeTypeNmEn"),
                        storeEntity.countryCd.as("countryCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.countryCd)), "countryNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.countryCd)), "countryNmEn"),
                        storeEntity.cityCd.as("cityCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(storeEntity.cityCd)), "cityNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(storeEntity.cityCd)), "cityNmEn"),
                        storeEntity.name,
                        storeEntity.contractor,
                        storeEntity.contractPeriod,
                        storeEntity.openDate,
                        storeEntity.transferDate,
                        storeEntity.closureDate,
                        storeEntity.boothCount,
                        storeEntity.boothType,
                        storeEntity.bizRegNo,
                        storeEntity.representative,
                        storeEntity.phone,
                        storeEntity.email,
                        storeEntity.internet,
                        storeEntity.revenueShare,
                        storeEntity.hqRoyalty,
                        storeEntity.omCost,
                        storeEntity.address,
                        storeEntity.printer,
                        storeEntity.signageYn,
                        storeEntity.note,
                        storeEntity.netArea,
                        storeEntity.sdKmMr,
                        storeEntity.bdc,
                        storeEntity.createDate
                ))
                .from(storeEntity)
                .where(storeEntity.id.eq(id))
                .fetchOne());
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, storeEntity, "createDate");
                        orders.add(orderUserId);
                    }
                    case "brandCd" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, storeEntity, "brandCd");
                        orders.add(orderUserId);
                    }
                    case "storeTypeCd" -> {
                        OrderSpecifier<?> orderName = queryDSLConfig.getSortedColumn(direction, storeEntity, "storeTypeCd");
                        orders.add(orderName);
                    }
                    case "countryCd" -> {
                        OrderSpecifier<?> orderDepartment = queryDSLConfig.getSortedColumn(direction, storeEntity, "countryCd");
                        orders.add(orderDepartment);
                    }
                    case "cityCd" -> {
                        OrderSpecifier<?> orderDepartment = queryDSLConfig.getSortedColumn(direction, storeEntity, "cityCd");
                        orders.add(orderDepartment);
                    }
                    default -> {
                    }
                }
            }
        }
        return orders;
    }
}
