package com.shoescms.domain.cart.service;

import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.dto.GioHangChiTietResDto;
import com.shoescms.domain.cart.dto.GioHangResDto;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.model.GioHangModel;
import com.shoescms.domain.product.entitis.SanPhamBienThe;

import java.util.List;

public interface GioHangService {
    GioHangResDto findById(Long id);
    GioHang findCartByUserId(Long userEntity);

    List<GioHangChiTietResDto> gioHangCuaToi(Long userEntity);

    GioHang add(GioHang gioHang);

    void remove(Long spBienTheId, Long userId);

    SanPhamBienThe getBienTheBySanPhamId(Long sanPhamBienThe);

    GioHangChiTietDto addItem(GioHangChiTietModel model);

    List<GioHangChiTietResDto> dongBoGioHang(List<GioHangChiTietModel> models, Long userId);
}
