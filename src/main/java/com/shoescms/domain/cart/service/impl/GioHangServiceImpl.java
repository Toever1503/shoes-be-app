package com.shoescms.domain.cart.service.impl;

import com.shoescms.domain.cart.dto.GioHangResDto;
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
    public GioHangResDto findById(Long id) {
        GioHang entity = this.gioHangRepository.findById(id).orElse(null);
        if(entity == null){
            return null;
        }
        return GioHangResDto.class.cast(entity);
    }

    @Override
    public GioHang findByUserEntity(Long userEntity) {
        return gioHangRepository.findByUserEntity(userEntity);
    }

    @Override
    public GioHang add(GioHang gioHang) {
        return this.gioHangRepository.saveAndFlush(gioHang);
    }

}
