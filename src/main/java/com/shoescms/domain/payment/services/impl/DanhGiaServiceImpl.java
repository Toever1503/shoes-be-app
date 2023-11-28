package com.shoescms.domain.payment.services.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.domain.payment.dtos.DanhGiaDto;
import com.shoescms.domain.payment.entities.DanhGiaEntity;
import com.shoescms.domain.payment.repositories.IDanhGiaRepository;
import com.shoescms.domain.payment.services.IDanhGiaService;
import com.shoescms.domain.user.dto.UsermetaDto;
import com.shoescms.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.shoescms.domain.payment.entities.QChiTietDonHangEntity.chiTietDonHangEntity;
import static com.shoescms.domain.payment.entities.QDanhGiaEntity.danhGiaEntity;

@Service
public class DanhGiaServiceImpl implements IDanhGiaService {

    @Autowired
    private IDanhGiaRepository repo;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private UserRepository userRepository;

    @Override
    public DanhGiaEntity create(DanhGiaEntity danhGia) {
        return repo.save(danhGia);
    }

    @Override
    public List<DanhGiaEntity> findByIds(List<Long> ids) {
        return repo.findByIds(ids);
    }

    @Override
    public Page<DanhGiaDto> layDanhGiaChoSp(Long id, String q, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(chiTietDonHangEntity.spId.eq(id));
        if(!ObjectUtils.isEmpty(q))
            builder.and(danhGiaEntity.binhLuan.contains(q));

        JPAQuery<DanhGiaEntity> jpaQuery = jpaQueryFactory.selectFrom(danhGiaEntity)
                .join(chiTietDonHangEntity)
                .on(chiTietDonHangEntity.id.eq(danhGiaEntity.donHangChiTietId))
                .where(builder)
                .orderBy(danhGiaEntity.ngayTao.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<DanhGiaDto> content = jpaQuery
                .fetch()
                .stream()
                .map(DanhGiaDto::toDto)
                .peek(item -> item.setNguoiTao(UsermetaDto.toDto(userRepository.findById(item.getNguoiTaoId()).orElse(null))))
                .toList();
        return new PageImpl<>(content, pageable, jpaQuery.fetchCount());
    }

    @Override
    public void xoaDanhGia(Long id) {
        try{
            repo.deleteById(id);
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
