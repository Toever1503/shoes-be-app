package com.shoescms.domain.admin.service;

import com.shoescms.domain.admin.dto.VoucherDTO;
import com.shoescms.domain.admin.entitis.Voucher;
import com.shoescms.domain.admin.models.VoucherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IVoucherService {
    Page<Voucher> filterEntities(Pageable pageable, Specification<Voucher> specification);

    VoucherDTO add(VoucherModel voucherModel);

    VoucherDTO update(VoucherModel voucherModel);

    boolean deleteById(Long id);

    Voucher getById(Long id);
}
