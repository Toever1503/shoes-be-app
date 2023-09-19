package com.shoescms.domain.product.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.domain.product.dto.DanhMucDTO;
import com.shoescms.domain.product.entitis.DMGiay;
import com.shoescms.domain.product.entitis.QDMGiay;
import com.shoescms.domain.product.entitis.QSanPham;
import com.shoescms.domain.product.models.DanhMucGiayModel;
import com.shoescms.domain.product.repository.DanhMucRepository;
import com.shoescms.domain.product.service.IDanhMucGiayService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class IDMGiayServiceImpl implements IDanhMucGiayService {

    @Autowired
    @Lazy
    DanhMucRepository danhMucRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DMGiay> filterEntities(Pageable pageable, Specification<DMGiay> specification) {
        return this.danhMucRepository.findAll(specification, pageable);
    }

    @Override
    public DanhMucDTO add(DanhMucGiayModel danhMucGiayModel) {
        DMGiay dmGiay = DMGiay.builder()
                .tenDanhMuc(danhMucGiayModel.getTenDanhMuc())
                .slug(danhMucGiayModel.getSlug())
                .build();
        danhMucRepository.saveAndFlush(dmGiay);
        return DanhMucDTO.toDTO(dmGiay);

    }

    @Override
    public DanhMucDTO update(DanhMucGiayModel danhMucGiayModel) {
        DMGiay dmGiay = danhMucRepository.findById(danhMucGiayModel.getId()).orElse(null);
        if (dmGiay != null) {
            dmGiay.setTenDanhMuc(danhMucGiayModel.getTenDanhMuc());
            dmGiay.setSlug(ASCIIConverter.utf8ToAscii(danhMucGiayModel.getTenDanhMuc()));
            danhMucRepository.saveAndFlush(dmGiay);

        }
        return DanhMucDTO.toDTO(dmGiay);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            DMGiay entity = this.getById(id);
            entity.setNgayXoa(LocalDateTime.now());
            this.danhMucRepository.saveAndFlush(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<DanhMucDTO> getDanhMucs(String tenDanhMuc, String slug,Pageable pageable) {
        List<DMGiay> dmGiays = danhMucRepository.findAll((Specification<DMGiay>) (root, query, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (!StringUtils.isEmpty(tenDanhMuc)) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("tenDanhMuc"), "%" + tenDanhMuc + "%"));
            }
            if(!StringUtils.isEmpty(slug)){
                p = criteriaBuilder.and(p,criteriaBuilder.like(root.get("slug"),"%" + slug +"%"));
            }
            query.orderBy(criteriaBuilder.desc(root.get("tenDanhMuc")), criteriaBuilder.asc(root.get("id")));
            return p;
        }, pageable).getContent();
        return DanhMucDTO.convertToTDO(dmGiays);
    }

    @Override
    public Page<DanhMucDTO> locDanhMuc(String tenDanhMuc, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        if(!ObjectUtils.isEmpty(tenDanhMuc))
            builder.and(QDMGiay.dMGiay.tenDanhMuc.contains(tenDanhMuc));

        builder.and(QDMGiay.dMGiay.ngayXoa.isNull());
        List<DanhMucDTO> content = jpaQueryFactory
                .selectDistinct(
                        QDMGiay.dMGiay.id,
                        QDMGiay.dMGiay.tenDanhMuc,
                        QDMGiay.dMGiay.slug,
                        QDMGiay.dMGiay.ngayTao,
                        ExpressionUtils.as(jpaQueryFactory.select(QSanPham.sanPham.id.countDistinct().castToNum(Integer.class)).from(QSanPham.sanPham).where(QSanPham.sanPham.dmGiay.id.eq(QDMGiay.dMGiay.id)), "soSp")
                )
                .from(QDMGiay.dMGiay)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QDMGiay.dMGiay.id.desc())
                .fetch()
                .stream()
                .map(tuple -> DanhMucDTO
                        .builder()
                        .id(tuple.get(QDMGiay.dMGiay.id))
                        .tenDanhMuc(tuple.get(QDMGiay.dMGiay.tenDanhMuc))
                        .slug(tuple.get(QDMGiay.dMGiay.slug))
                        .ngayTao(tuple.get(QDMGiay.dMGiay.ngayTao))
                        .soSp(tuple.get(4, Integer.class))
                        .build())
                .toList();

        Long total = Optional.ofNullable(
                jpaQueryFactory.select(QDMGiay.dMGiay.id.countDistinct())
                        .from(QDMGiay.dMGiay)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    public DMGiay getById(Long id) {
        return danhMucRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
    }
}
