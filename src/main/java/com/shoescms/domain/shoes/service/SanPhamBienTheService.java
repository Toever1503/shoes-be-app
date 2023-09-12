package com.shoescms.domain.shoes.service;

import com.shoescms.domain.shoes.dto.SanPhamBienTheDTO;
import com.shoescms.domain.shoes.models.SanPhamBienTheModel;

public interface SanPhamBienTheService {


    SanPhamBienTheDTO add(SanPhamBienTheModel sanPhamBienTheModel);

    SanPhamBienTheDTO update(SanPhamBienTheModel sanPhamBienTheModel);

    boolean deleteById(Long id);
}
