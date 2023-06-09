package com.photoism.cms.domain.popup.entity;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.popup.dto.PopUpDetailResDto;
import com.photoism.cms.domain.popup.dto.PopUpResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.photoism.cms.domain.popup.entity.QPopUpEntity.popUpEntity;

@Repository
@RequiredArgsConstructor
public class PopUpQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<PopUpResDto> findPopUpList(Boolean isShow, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (isShow != null)    builder.and(popUpEntity.isShow.eq(isShow));
        builder.and(popUpEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<PopUpResDto> content = jpaQueryFactory
                .select(Projections.constructor(PopUpResDto.class,
                        popUpEntity.id,
                        popUpEntity.title,
                        popUpEntity.isShow,
                        popUpEntity.ignoreTime,
                        popUpEntity.startDate,
                        popUpEntity.endDate,
                        popUpEntity.layerLeft,
                        popUpEntity.layerTop,
                        popUpEntity.layerWidth,
                        popUpEntity.layerHeight,
                        popUpEntity.createDate
                ))
                .from(popUpEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(Wildcard.count)
                .from(popUpEntity)
                .where(builder)
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }

    public Optional<PopUpDetailResDto> getDetail(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(PopUpDetailResDto.class,
                        popUpEntity.id,
                        popUpEntity.title,
                        popUpEntity.isShow,
                        popUpEntity.ignoreTime,
                        popUpEntity.startDate,
                        popUpEntity.endDate,
                        popUpEntity.layerLeft,
                        popUpEntity.layerTop,
                        popUpEntity.layerWidth,
                        popUpEntity.layerHeight,
                        popUpEntity.content,
                        popUpEntity.createDate
                ))
                .from(popUpEntity)
                .where(popUpEntity.id.eq(id))
                .fetchOne());
    }

    public List<PopUpDetailResDto> get() {
        LocalDate now = LocalDate.now();
        return jpaQueryFactory
                .select(Projections.constructor(PopUpDetailResDto.class,
                        popUpEntity.id,
                        popUpEntity.title,
                        popUpEntity.isShow,
                        popUpEntity.ignoreTime,
                        popUpEntity.startDate,
                        popUpEntity.endDate,
                        popUpEntity.layerLeft,
                        popUpEntity.layerTop,
                        popUpEntity.layerWidth,
                        popUpEntity.layerHeight,
                        popUpEntity.content,
                        popUpEntity.createDate
                ))
                .from(popUpEntity)
                .where(popUpEntity.isShow.eq(true), popUpEntity.startDate.loe(now), popUpEntity.endDate.goe(now))
                .orderBy(popUpEntity.createDate.desc())
                .fetch();
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, popUpEntity, "createDate");
                        orders.add(orderUserId);
                    }
                    case "isShow" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, popUpEntity, "isShow");
                        orders.add(orderUserId);
                    }
                    case "startDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, popUpEntity, "startDate");
                        orders.add(orderUserId);
                    }
                    case "endDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, popUpEntity, "endDate");
                        orders.add(orderUserId);
                    }
                    default -> {
                    }
                }
            }
        }
        return orders;
    }
}
