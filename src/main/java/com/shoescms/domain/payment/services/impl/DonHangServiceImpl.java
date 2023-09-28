package com.shoescms.domain.payment.services.impl;

import com.shoescms.domain.payment.dtos.DonHangDto;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.payment.services.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DonHangServiceImpl implements IDonHangService {

    @Autowired
    private IDonHangRepository donHangRepository;

    @Override
    public Page<DonHangDto> filterEntities(Pageable pageable, Specification<DonHangEntity> specification) {
        Page<DonHangEntity> donHangDtoPage = donHangRepository.findAll(specification, pageable);
        return donHangDtoPage.map(item -> DonHangDto.toDto(item));
    }
}
