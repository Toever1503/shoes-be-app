package com.photoism.cms.domain.community.repository;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.community.dto.CommunityDetailResDto;
import com.photoism.cms.domain.community.dto.CommunityResDto;
import com.photoism.cms.domain.file.dto.FileResDto;
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

import static com.photoism.cms.domain.community.entity.QCommunityEntity.communityEntity;
import static com.photoism.cms.domain.community.entity.QCommunityFileEntity.communityFileEntity;
import static com.photoism.cms.domain.file.entity.QFileEntity.fileEntity;

@Repository
@RequiredArgsConstructor
public class CommunityQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<CommunityResDto> findList(String div, String title, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (div != null)    builder.and(communityEntity.div.eq(div));
        if (title != null)    builder.and(communityEntity.title.contains(title));
        builder.and(communityEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<CommunityResDto> content = jpaQueryFactory
                .select(Projections.constructor(CommunityResDto.class,
                        communityEntity.id,
                        communityEntity.title,
                        ExpressionUtils.as(JPAExpressions.select(communityFileEntity.id).from(communityFileEntity).where(communityFileEntity.community.eq(communityEntity)).exists(), "fileYn"),
                        communityEntity.readCount,
                        communityEntity.createDate
                ))
                .from(communityEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(Wildcard.count)
                .from(communityEntity)
                .where(builder)
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }

    public Optional<CommunityDetailResDto> getDetail(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(CommunityDetailResDto.class,
                        communityEntity.id,
                        communityEntity.title,
                        communityEntity.content,
                        communityEntity.readCount,
                        communityEntity.createDate
                ))
                .from(communityEntity)
                .where(communityEntity.id.eq(id))
                .fetchOne());
    }

    public List<FileResDto> getFiles(Long id) {
        return jpaQueryFactory
                .select(Projections.constructor(FileResDto.class,
                        fileEntity.id,
                        fileEntity.name,
                        fileEntity.alterName,
                        fileEntity.path,
                        fileEntity.del
                ))
                .from(communityFileEntity)
                .leftJoin(fileEntity)
                    .on(fileEntity.id.eq(communityFileEntity.fileId))
                .where(communityFileEntity.community.id.eq(id))
                .orderBy(fileEntity.id.asc())
                .fetch();
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, communityEntity, "createDate");
                        orders.add(orderUserId);
                    }
                    case "title" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, communityEntity, "title");
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
