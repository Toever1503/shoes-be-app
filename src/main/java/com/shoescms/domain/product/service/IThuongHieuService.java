package com.shoescms.domain.product.service;

import com.shoescms.domain.product.dto.DanhMucDTO;
import com.shoescms.domain.product.models.ThuongHieuModel;
import com.shoescms.domain.product.dto.ThuongHieuDTO;
import com.shoescms.domain.product.entitis.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IThuongHieuService{

    Page<ThuongHieu> filterEntities(Pageable pageable, Specification<ThuongHieu> specification);

    ThuongHieuDTO add(ThuongHieuModel thuongHieuModel);

    ThuongHieuDTO update(ThuongHieuModel thuongHieuModel);

    boolean deleteById(Long id);

    List<ThuongHieuDTO> getThuongHieus(String tenThuongHieu, String slug,Pageable pageable);

    Page<ThuongHieuDTO> locThuongHieu(String tenThuongHieu, Pageable pageable);
}
