package com.shoescms.domain.cart.service;

import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.dto.GioHangResDto;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.model.GioHangModel;
import com.shoescms.domain.product.entitis.SanPhamBienThe;

import java.util.List;

public interface GioHangService {
    GioHangResDto findById(Long id);

    GioHangResDto findByUserEntity(Long userEntity);

    GioHangResDto add(GioHang gioHang);

    void remove(Long itemId, Long userId);

    SanPhamBienThe getBienTheBySanPhamId(Long sanPhamBienThe);

    GioHangChiTietDto addItem(GioHangChiTietModel model);
}
