package com.photoism.cms.domain.auth.repository;

import com.photoism.cms.domain.admin.dto.PrivilegeResDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.photoism.cms.domain.auth.entity.QPrivilegeEntity.privilegeEntity;
import static com.photoism.cms.domain.etc.entity.QCodeEntity.codeEntity;

@Repository
@RequiredArgsConstructor
public class PrivilegeQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<PrivilegeResDto> getPrivileges(String roleCd) {
        return jpaQueryFactory
                .select(Projections.constructor(PrivilegeResDto.class,
                        privilegeEntity.privilegeCd,
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameKr).from(codeEntity).where(codeEntity.code.eq(privilegeEntity.privilegeCd)), "privilegeNmKr"),
                        ExpressionUtils.as(JPAExpressions.select(codeEntity.nameEn).from(codeEntity).where(codeEntity.code.eq(privilegeEntity.privilegeCd)), "privilegeNmEn")
                ))
                .from(privilegeEntity)
                .where(privilegeEntity.roleCd.eq(roleCd))
                .fetch();
    }
}
