package com.shoescms.domain.payment.services.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.domain.payment.entities.DanhGia;
import com.shoescms.domain.payment.repositories.IDanhGiaRepository;
import com.shoescms.domain.payment.services.IDanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shoescms.domain.payment.entities.QChiTietDonHangEntity.chiTietDonHangEntity;
import static com.shoescms.domain.payment.entities.QDanhGia.danhGia;

@Service
public class DanhGiaServiceImpl implements IDanhGiaService {

    @Autowired
    private IDanhGiaRepository repo;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public DanhGia create(DanhGia danhGia) {
        return repo.save(danhGia);
    }

    @Override
    public List<DanhGia> findByIds(List<Long> ids) {
        return repo.findByIds(ids);
    }

    @Override
    public List<DanhGia> layDanhGiaChoSp(Long id) {
        return jpaQueryFactory.selectFrom(danhGia)
                .join(chiTietDonHangEntity)
                .on(chiTietDonHangEntity.id.eq(danhGia.donHangChiTietId))
                .where(chiTietDonHangEntity.spId.eq(id))
                .fetch();
    }
}
