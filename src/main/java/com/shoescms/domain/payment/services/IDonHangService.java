package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.DonHangDto;
import com.shoescms.domain.payment.dtos.ETrangThaiDonHang;
import com.shoescms.domain.payment.dtos.ThemMoiDonHangReqDto;
import com.shoescms.domain.payment.entities.DonHangEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IDonHangService {
    Page<DonHangDto> filterEntities(Pageable pageable, Specification<DonHangEntity> specification);

    DonHangDto chiTietDonHang(Long id);

    void capNhatTrangThai(Long id, ETrangThaiDonHang trangThai, Long userId);

    DonHangDto themMoiDonHang(ThemMoiDonHangReqDto reqDto);

    Page<DonHangEntity> findByNguoiMuaId(Long nguoiMuaId, ETrangThaiDonHang trangThai, Pageable pageable);
    Page<DonHangEntity> findByNguoiMuaId(Long nguoiMuaId, Pageable pageable);


    List<DonHangDto> traCuuDonHang(String q);
}
