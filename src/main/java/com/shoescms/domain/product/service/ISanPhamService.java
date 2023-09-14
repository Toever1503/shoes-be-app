package com.shoescms.domain.product.service;

import com.shoescms.domain.product.dto.SanPhamDto;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.models.SanPhamModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ISanPhamService {
    Page<SanPhamDto> filterEntities(Pageable pageable, Specification<SanPham> specification);

    Page<SanPham> getAll(Pageable pageable);


    SanPhamDto add(SanPhamModel model);

    SanPhamDto update(SanPhamModel model);

    boolean deleteById(Long id);
}
