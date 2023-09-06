package com.shoescms.domain.shoes.service.impl;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.models.DanhMucGiayModel;
import com.shoescms.domain.shoes.repository.DanhMucRepository;
import com.shoescms.domain.shoes.service.IDanhMucGiayService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class IDMGiayServiceImpl implements IDanhMucGiayService {

    @Autowired
    @Lazy
    DanhMucRepository danhMucRepository;

    @Override
    public Page<DMGiay> filterEntities(Pageable pageable, Specification<DMGiay> specification) {
        return this.danhMucRepository.findAll(specification, pageable);
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
        if (dmGiay != null) {
            dmGiay.setTenDanhMuc(danhMucGiayModel.getTenDanhMuc());
            dmGiay.setSlug(danhMucGiayModel.getSlug());
            danhMucRepository.saveAndFlush(dmGiay);

        }
        return DanhMucDTO.toDTO(dmGiay);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            DMGiay entity = this.getById(id);
            this.danhMucRepository.delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<DanhMucDTO> getDanhMucs(String tenDanhMuc, String slug,Pageable pageable) {
        List<DMGiay> dmGiays = danhMucRepository.findAll((Specification<DMGiay>) (root, query, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (!StringUtils.isEmpty(tenDanhMuc)) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("tenDanhMuc"), "%" + tenDanhMuc + "%"));
            }
            if(!StringUtils.isEmpty(slug)){
                p = criteriaBuilder.and(p,criteriaBuilder.like(root.get("slug"),"%" + slug +"%"));
            }
            query.orderBy(criteriaBuilder.desc(root.get("tenDanhMuc")), criteriaBuilder.asc(root.get("id")));
            return p;
        }, pageable).getContent();
        return DanhMucDTO.convertToTDO(dmGiays);
    }

    public DMGiay getById(Long id) {
        return danhMucRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
    }
}
