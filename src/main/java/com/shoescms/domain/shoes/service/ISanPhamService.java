package com.shoescms.domain.shoes.service;

import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.models.SanPhamModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ISanPhamService {
    Page<SanPham> filterEntities(Pageable pageable, Specification<SanPham> specification);

    Page<SanPham> getAll(Pageable pageable);

    Page<SanPhamDto> findByThuongHieuIdAndDmGiayId(Long idThuongHieu, Long idDanhMucGiay, Pageable pageable);
    SanPhamDto add(SanPhamModel model);

    SanPhamDto update(SanPhamModel model);

    boolean deleteById(Long id);
}
