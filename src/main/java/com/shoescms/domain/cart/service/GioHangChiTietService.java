package com.shoescms.domain.cart.service;

import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.product.entitis.SanPhamBienThe;

import java.util.Optional;

public interface GioHangChiTietService {

    GioHangChiTietDto add(GioHangChiTietModel gioHangChiTiet);

    boolean remove(Long id);

    SanPhamBienThe getBienTheBySanPhamId(Long id);
}
