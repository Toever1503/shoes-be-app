package com.shoescms.domain.cart.service;

import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.model.GioHangChiTietModel;

public interface GioHangChiTietService {

    GioHangChiTietDto add(GioHangChiTietModel gioHangChiTiet);

    boolean remove(Long id);
}
