package com.shoescms.domain.cart.service;

import com.shoescms.domain.cart.dto.GioHangDto;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.model.GioHangModel;

import java.util.Optional;

public interface GioHangService {
    GioHang findById(Long id);

    GioHangDto add(GioHangModel gioHang);
}
