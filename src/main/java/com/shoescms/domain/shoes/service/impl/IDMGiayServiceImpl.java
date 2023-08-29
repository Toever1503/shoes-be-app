package com.shoescms.domain.shoes.service.impl;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.models.DanhMucGiayModel;
import com.shoescms.domain.shoes.repository.DanhMucRepository;
import com.shoescms.domain.shoes.service.IDanhMucGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class IDMGiayServiceImpl  implements IDanhMucGiayService {

    @Autowired
    @Lazy
    DanhMucRepository danhMucRepository;
    @Override
    public Page<DMGiay> filterEntities(Pageable pageable, Specification<DMGiay> specification) {
        return this.danhMucRepository.findAll(specification,pageable);
    }

    @Override
    public DanhMucDTO add(DanhMucGiayModel danhMucGiayModel) {
       DMGiay dmGiay = DMGiay.builder()
               .tenDanhMuc(danhMucGiayModel.getTenDanhMuc())
               .slug(danhMucGiayModel.getSlug())
               .build();
       danhMucRepository.saveAndFlush(dmGiay);
       return DanhMucDTO.toDTO(dmGiay);

    }

    @Override
    public DanhMucDTO update(DanhMucGiayModel danhMucGiayModel) {
        DMGiay dmGiay = danhMucRepository.findById(danhMucGiayModel.getId()).orElse(null);
        if (dmGiay!=null){
            dmGiay.setTenDanhMuc(danhMucGiayModel.getTenDanhMuc());
            dmGiay.setSlug(danhMucGiayModel.getSlug());
            danhMucRepository.saveAndFlush(dmGiay);

        }
        return  DanhMucDTO.toDTO(dmGiay);
    }

    @Override
    public boolean deleteById(Long id) {
      try {
          DMGiay entity= this.getById(id);
          this.danhMucRepository.delete(entity);
          return true;
      }catch (Exception e){
          return false;
      }
    }

    public DMGiay getById(Long id){
        return  danhMucRepository.findById(id).orElseThrow(() ->new RuntimeException("22"));
    }
}
