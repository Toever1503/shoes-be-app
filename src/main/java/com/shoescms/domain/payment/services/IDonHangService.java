package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.DonHangDto;
import com.shoescms.domain.payment.entities.DonHangEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IDonHangService {
    Page<DonHangDto> filterEntities(Pageable pageable, Specification<DonHangEntity> specification);

    DonHangDto chiTietDonHang(Long id);
}
