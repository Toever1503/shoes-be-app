package com.photoism.cms.domain.user.repository;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.user.dto.UserDetailResDto;
import com.photoism.cms.domain.user.dto.UserResDto;
import com.photoism.cms.domain.user.entity.UserEntity;
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

import static com.photoism.cms.domain.auth.entity.QRoleEntity.roleEntity;
import static com.photoism.cms.domain.etc.entity.QCodeEntity.codeEntity;
import static com.photoism.cms.domain.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<UserDetailResDto> getDetail(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(UserDetailResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        roleEntity.roleCd.as("roleCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmEn"),
                        userEntity.phone,
                        userEntity.email,
                        userEntity.createDate
                ))
                .from(userEntity)
                .where(userEntity.id.eq(id))
                .fetchOne());
    }

    public Page<UserResDto> findStaffUserList(String userId, String name, String roleCd, String phone, String email, Boolean approved, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null)         builder.and(userEntity.userId.eq(userId));
        if (name != null)           builder.and(userEntity.name.contains(name));
        if (roleCd != null)         builder.and(roleEntity.roleCd.eq(roleCd));
        if (phone != null)          builder.and(userEntity.phone.contains(phone));
        if (email != null)          builder.and(userEntity.email.contains(email));
        if (approved != null)       builder.and(userEntity.approved.eq(approved));
        builder.and(userEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<UserResDto> content = jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        roleEntity.roleCd.as("roleCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmEn"),
                        userEntity.phone,
                        userEntity.email,
                        userEntity.approved,
                        userEntity.createDate
                ))
                .from(userEntity)
                .join(roleEntity)
                    .on(roleEntity.roleCd.contains("ROLE_STORE").not(), roleEntity.user.eq(userEntity))
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public Page<UserResDto> findStoreUserList(String userId, String name, String phone, String email, Boolean approved, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null)     builder.and(userEntity.userId.eq(userId));
        if (name != null)       builder.and(userEntity.name.contains(name));
        if (phone != null)      builder.and(userEntity.phone.contains(phone));
        if (email != null)      builder.and(userEntity.email.contains(email));
        if (approved != null)   builder.and(userEntity.approved.eq(approved));
        builder.and(userEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<UserResDto> content = jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        roleEntity.roleCd.as("roleCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmEn"),
                        userEntity.phone,
                        userEntity.email,
                        userEntity.approved,
                        userEntity.createDate
                ))
                .from(userEntity)
                .join(roleEntity)
                    .on(roleEntity.roleCd.contains("ROLE_STORE"), roleEntity.user.eq(userEntity))
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public List<UserResDto> exportStaffUserList(String string) {
        return jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        roleEntity.roleCd.as("roleCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmEn"),
                        userEntity.phone,
                        userEntity.email,
                        userEntity.approved,
                        userEntity.createDate
                ))
                .from(userEntity)
                .join(roleEntity)
                .on(roleEntity.roleCd.contains("ROLE_STORE").not(), roleEntity.user.eq(userEntity))
                .where(userEntity.del.isFalse())
                .orderBy(userEntity.createDate.desc())
                .fetch();
    }

    public List<UserResDto> exportStoreUserList(String string) {
        return jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        roleEntity.roleCd.as("roleCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmEn"),
                        userEntity.phone,
                        userEntity.email,
                        userEntity.approved,
                        userEntity.createDate
                ))
                .from(userEntity)
                .join(roleEntity)
                .on(roleEntity.roleCd.contains("ROLE_STORE"), roleEntity.user.eq(userEntity))
                .where(userEntity.del.isFalse())
                .orderBy(userEntity.createDate.desc())
                .fetch();
    }

    public List<UserResDto> getUserForStoreMapping(String userId, String name) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null)     builder.or(userEntity.userId.contains(userId));
        if (name != null)       builder.or(userEntity.name.contains(name));

        return jpaQueryFactory
                .select(Projections.constructor(UserResDto.class,
                        userEntity.id,
                        userEntity.userId,
                        userEntity.name,
                        roleEntity.roleCd.as("roleCd"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(roleEntity.roleCd)), "roleNmEn"),
                        userEntity.phone,
                        userEntity.email,
                        userEntity.approved,
                        userEntity.createDate
                ))
                .from(userEntity)
                .join(roleEntity)
                .on(roleEntity.user.eq(userEntity))
                .where(builder)
                .fetch();
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, userEntity, "createDate");
                        orders.add(orderUserId);
                    }
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
