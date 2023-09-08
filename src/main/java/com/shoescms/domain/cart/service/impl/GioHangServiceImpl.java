package com.shoescms.domain.cart.service.impl;

import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.model.GioHangModel;
import com.shoescms.domain.cart.repository.GioHangRepository;
import com.shoescms.domain.cart.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public GioHang findById(Long id) {
        return gioHangRepository.findById(id).orElse(null);
    }

}
