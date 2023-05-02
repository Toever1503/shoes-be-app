package com.photoism.cms.domain.user.repository;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.user.dto.UserResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
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

import static com.photoism.cms.domain.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<UserResDto> findStaffUserList(String userId, String name, String department, String phone, String email, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null)     builder.and(userEntity.userId.eq(userId));
        if (name != null)       builder.and(userEntity.name.contains(name));
        if (department != null) builder.and(userEntity.department.contains(department));
        if (phone != null)      builder.and(userEntity.phone.contains(phone));
        if (email != null)      builder.and(userEntity.email.contains(email));
        builder.and(userEntity.storeUser.isFalse());
        builder.and(userEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<UserResDto> content = jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        userEntity.department,
                        userEntity.phone,
                        userEntity.email,
                        userEntity.createDate))
                .from(userEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public Page<UserResDto> findStoreUserList(String userId, String name, String phone, String email, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null)     builder.and(userEntity.userId.eq(userId));
        if (name != null)       builder.and(userEntity.name.contains(name));
        if (phone != null)      builder.and(userEntity.phone.contains(phone));
        if (email != null)      builder.and(userEntity.email.contains(email));
        builder.and(userEntity.storeUser.isTrue());
        builder.and(userEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<UserResDto> content = jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        userEntity.department,
                        userEntity.phone,
                        userEntity.email,
                        userEntity.createDate))
                .from(userEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "userId" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, userEntity, "userId");
                        orders.add(orderUserId);
                    }
                    case "name" -> {
                        OrderSpecifier<?> orderName = queryDSLConfig.getSortedColumn(direction, userEntity, "name");
                        orders.add(orderName);
                    }
                    case "department" -> {
                        OrderSpecifier<?> orderDepartment = queryDSLConfig.getSortedColumn(direction, userEntity, "department");
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
