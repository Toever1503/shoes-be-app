package com.shoescms.domain.product.service.impl;

import com.shoescms.common.utils.ASCIIConverter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.common.utils.ASCIIConverter;
import com.shoescms.domain.product.dto.ThuongHieuDTO;
import com.shoescms.domain.product.entitis.QSanPham;
import com.shoescms.domain.product.entitis.QThuongHieu;
import com.shoescms.domain.product.entitis.ThuongHieu;
import com.shoescms.domain.product.models.ThuongHieuModel;
import com.shoescms.domain.product.repository.ThuogHieuRepository;
import com.shoescms.domain.product.service.IThuongHieuService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IThuongHieuServiceImpl implements IThuongHieuService {
    @Autowired
    ThuogHieuRepository thuogHieuRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ThuongHieu> filterEntities(Pageable pageable, Specification<ThuongHieu> specification) {
        return thuogHieuRepository.findAll(specification, pageable);
    }

    @Override
    public ThuongHieuDTO add(ThuongHieuModel thuongHieuModel) {
       ThuongHieu thuongHieu = ThuongHieu.builder()
               .tenThuongHieu(thuongHieuModel.getTenThuongHieu())
               .slug(ASCIIConverter.utf8ToAscii(thuongHieuModel.getTenThuongHieu()))
               .build();
                thuogHieuRepository.save(thuongHieu);
       return ThuongHieuDTO.toDTO(thuongHieu);
    }

    @Override
    public ThuongHieuDTO update(ThuongHieuModel thuongHieuModel) {
        ThuongHieu th = thuogHieuRepository.findById(thuongHieuModel.getId()).orElse(null);
        if (th != null) {
            th.setTenThuongHieu(thuongHieuModel.getTenThuongHieu());
            th.setSlug(ASCIIConverter.utf8ToAscii(thuongHieuModel.getTenThuongHieu()));
            thuogHieuRepository.save(th);
        }
        return ThuongHieuDTO.toDTO(th);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            ThuongHieu entity = this.getById(id);
            entity.setNgayXoa(LocalDateTime.now());
            this.thuogHieuRepository.saveAndFlush(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ThuongHieuDTO> getThuongHieus(String tenThuongHieu, String slug, Pageable pageable) {
        List<ThuongHieu> thuongHieu = thuogHieuRepository.findAll((Specification<ThuongHieu>) (root, query, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (!StringUtils.isEmpty(tenThuongHieu)) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("tenThuongHieu"), "%" + tenThuongHieu + "%"));
            }
            if (!StringUtils.isEmpty(slug)) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("slug"), "%" + slug + "%"));
            }
            query.orderBy(criteriaBuilder.desc(root.get("tenThuongHieu")), criteriaBuilder.asc(root.get("id")));
            return p;
        }, pageable).getContent();
        return ThuongHieuDTO.convertToTDO(thuongHieu);
    }

    @Override
    public Page<ThuongHieuDTO> locThuongHieu(String tenThuongHieu, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(tenThuongHieu))
            builder.and(QThuongHieu.thuongHieu.tenThuongHieu.contains(tenThuongHieu));

        builder.and(QThuongHieu.thuongHieu.ngayXoa.isNull());
        List<ThuongHieuDTO> content = jpaQueryFactory.selectDistinct(
                        QThuongHieu.thuongHieu.id,
                        QThuongHieu.thuongHieu.tenThuongHieu,
                        QThuongHieu.thuongHieu.slug,
                        QThuongHieu.thuongHieu.ngayTao,
                        ExpressionUtils.as(jpaQueryFactory.select(QSanPham.sanPham.id.countDistinct().castToNum(Integer.class)).from(QSanPham.sanPham).where(QSanPham.sanPham.thuongHieu.id.eq(QThuongHieu.thuongHieu.id)), "soSp")
                )
                .from(QThuongHieu.thuongHieu)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QThuongHieu.thuongHieu.id.desc())
                .fetch()
                .stream()
                .map(tuple -> ThuongHieuDTO
                        .builder()
                        .id(tuple.get(QThuongHieu.thuongHieu.id))
                        .tenThuongHieu(tuple.get(QThuongHieu.thuongHieu.tenThuongHieu))
                        .slug(tuple.get(QThuongHieu.thuongHieu.slug))
                        .ngayTao(tuple.get(QThuongHieu.thuongHieu.ngayTao))
                        .soSp(tuple.get(4, Integer.class))
                        .build())
                .toList();

        Long total = Optional.ofNullable(
                jpaQueryFactory.select(QThuongHieu.thuongHieu.id.countDistinct())
                        .from(QThuongHieu.thuongHieu)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    public ThuongHieu getById(Long id) {
        return thuogHieuRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
    }

}
