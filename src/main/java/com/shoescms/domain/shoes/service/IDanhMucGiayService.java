package com.shoescms.domain.shoes.service;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.models.DanhMucGiayModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IDanhMucGiayService {

    Page<DMGiay> filterEntities(Pageable pageable, Specification<DMGiay> specification);

    DanhMucDTO add(DanhMucGiayModel danhMucGiayModel);

    DanhMucDTO update(DanhMucGiayModel danhMucGiayModel);

    boolean deleteById(Long id);
}
