package com.shoescms.domain.shoes.service;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ThuongHieuDTO;
import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.entitis.ThuongHieu;
import com.shoescms.domain.shoes.models.DanhMucGiayModel;
import com.shoescms.domain.shoes.models.ThuongHieuModel;
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
}
