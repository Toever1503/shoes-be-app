package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.DanhGiaDto;
import com.shoescms.domain.payment.entities.DanhGiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDanhGiaService {
    DanhGiaDTO create(DanhGiaReqDTO danhGia);

    List<DanhGiaEntity> findByIds(List<Long> ids);

        Double findRatingBySanPham(Long idSanPham);

    Page<DanhGiaDto> layDanhGiaChoSp(Long id, String q, Pageable pageable);

    void xoaDanhGia(Long id);
}
