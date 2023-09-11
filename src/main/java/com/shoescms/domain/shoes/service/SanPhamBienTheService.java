package com.shoescms.domain.shoes.service;

import com.shoescms.domain.shoes.dto.SanPhamBienTheDTO;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import com.shoescms.domain.shoes.models.SanPhamBienTheModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SanPhamBienTheService {

    Page<SanPhamBienTheDTO> filterEntities(Pageable pageable, Specification<SanPhamBienThe> specification);
    SanPhamBienTheDTO add(SanPhamBienTheModel sanPhamBienTheModel);

    SanPhamBienTheDTO update(SanPhamBienTheModel sanPhamBienTheModel);

    boolean deleteById(Long id);
}
