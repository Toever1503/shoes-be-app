package com.photoism.cms.domain.community.repository;

import com.photoism.cms.common.config.QueryDSLConfig;
import com.photoism.cms.domain.community.dto.NoticeDetailResDto;
import com.photoism.cms.domain.community.dto.NoticeResDto;
import com.photoism.cms.domain.file.dto.FileResDto;
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

import static com.photoism.cms.domain.community.entity.QNoticeEntity.noticeEntity;
import static com.photoism.cms.domain.community.entity.QNoticeFileEntity.noticeFileEntity;
import static com.photoism.cms.domain.file.entity.QFileEntity.fileEntity;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepository {
    private final QueryDSLConfig queryDSLConfig;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<NoticeResDto> findNoticeList(String title, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (title != null)    builder.and(noticeEntity.title.contains(title));
        builder.and(noticeEntity.del.isFalse());

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<NoticeResDto> content = jpaQueryFactory
                .select(Projections.constructor(NoticeResDto.class,
                        noticeEntity.id,
                        noticeEntity.title,
                        ExpressionUtils.as(JPAExpressions.select(noticeFileEntity.id).from(noticeFileEntity).where(noticeFileEntity.notice.eq(noticeEntity)).exists(), "fileYn"),
                        noticeEntity.readCount,
                        noticeEntity.createDate
                ))
                .from(noticeEntity)
                .where(builder)
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public Optional<NoticeDetailResDto> getNoticeDetail(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(NoticeDetailResDto.class,
                        noticeEntity.id,
                        noticeEntity.title,
                        noticeEntity.content,
                        noticeEntity.readCount,
                        noticeEntity.createDate
                ))
                .from(noticeEntity)
                .where(noticeEntity.id.eq(id))
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
                .from(noticeFileEntity)
                .leftJoin(fileEntity)
                    .on(fileEntity.id.eq(noticeFileEntity.fileId))
                .where(noticeFileEntity.notice.id.eq(id))
                .fetch();
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createDate" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, noticeEntity, "createDate");
                        orders.add(orderUserId);
                    }
                    case "title" -> {
                        OrderSpecifier<?> orderUserId = queryDSLConfig.getSortedColumn(direction, noticeEntity, "title");
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
