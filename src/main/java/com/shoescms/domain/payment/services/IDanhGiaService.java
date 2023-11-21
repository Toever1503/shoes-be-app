package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.entities.DanhGia;

import java.util.List;

public interface IDanhGiaService {
    DanhGia create(DanhGia danhGia);

    List<DanhGia> findByIds(List<Long> ids);

    List<DanhGia> layDanhGiaChoSp(Long id);
}
