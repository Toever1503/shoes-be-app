package com.photoism.cms.domain.kiosk.repository;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.kiosk.dto.KioskDetailResDto;
import com.photoism.cms.domain.kiosk.dto.KioskResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
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
import static com.photoism.cms.domain.kiosk.entity.QKioskEntity.kioskEntity;
import static com.photoism.cms.domain.store.entity.QStoreEntity.storeEntity;

@Repository
@RequiredArgsConstructor
public class KioskQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<KioskResDto> findKioskList(Long storeId, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (storeId != null)    builder.and(kioskEntity.storeId.eq(storeId));
        builder.and(kioskEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<KioskResDto> content = jpaQueryFactory
                .select(Projections.constructor(KioskResDto.class,
                        kioskEntity.storeId,
                        kioskEntity.id,
                        kioskEntity.deviceNo,
                        kioskEntity.deviceId,
                        kioskEntity.createDate
                        ))
                .from(kioskEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(Wildcard.count)
                .from(kioskEntity)
                .where(builder)
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }

    public Optional<KioskDetailResDto> getDetail(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(KioskDetailResDto.class,
                        kioskEntity.id,
                        kioskEntity.storeId,
                        kioskEntity.deviceNo,
                        kioskEntity.deviceId,
                        kioskEntity.license,
                        kioskEntity.anydesk,
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(kioskEntity.colorCd)), "colorNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(kioskEntity.colorCd)), "colorNmEn"),
                        kioskEntity.camera,
                        kioskEntity.cameraSerial,
                        kioskEntity.cameraLensZoom,
                        kioskEntity.cameraISO,
                        kioskEntity.cameraShutterSpeed,
                        kioskEntity.cameraAperture,
                        kioskEntity.cameraColorTemp,
                        kioskEntity.cameraWbCal,
                        kioskEntity.strobeIntensity,
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(kioskEntity.printerCd)), "printerNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(kioskEntity.printerCd)), "printerNmEn"),
                        kioskEntity.printerCnt,
                        kioskEntity.monitor,
                        kioskEntity.pc,
                        kioskEntity.ioBoard,
                        kioskEntity.internet,
                        kioskEntity.internetSerial,
                        kioskEntity.createDate
                ))
                .from(kioskEntity)
                .where(kioskEntity.id.eq(id))
                .fetchOne());
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, kioskEntity, "createDate");
                        orders.add(orderUserId);
                    }
                    case "storeId" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, kioskEntity, "storeId");
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
