package com.shoescms.domain.cart.service;

import com.shoescms.domain.cart.dto.GioHangResDto;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.model.GioHangModel;

public interface GioHangService {
    GioHangResDto findById(Long id);

    GioHang findByUserEntity(Long userEntity);

    GioHang add(GioHang gioHang);

}
