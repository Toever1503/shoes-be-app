package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.DanhGiaDTO;
import com.shoescms.domain.payment.dtos.DanhGiaReqDTO;
import com.shoescms.domain.payment.entities.DanhGia;

import java.util.List;

public interface IDanhGiaService {
    DanhGiaDTO create(DanhGiaReqDTO danhGia);

    List<DanhGia> findByIds(List<Long> ids);

    List<DanhGia> layDanhGiaChoSp(Long id);

    Double findRatingBySanPham(Long idSanPham);
}
