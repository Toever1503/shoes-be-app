package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.DanhGiaDto;
import com.shoescms.domain.payment.entities.DanhGiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDanhGiaService {
    DanhGiaEntity create(DanhGiaEntity danhGia);

    List<DanhGiaEntity> findByIds(List<Long> ids);

    Page<DanhGiaDto> layDanhGiaChoSp(Long id, String q, Pageable pageable);

    void xoaDanhGia(Long id);
}
