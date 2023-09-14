package com.shoescms.domain.product.service.impl;

import com.shoescms.domain.product.dto.ThuongHieuDTO;
import com.shoescms.domain.product.entitis.ThuongHieu;
import com.shoescms.domain.product.models.ThuongHieuModel;
import com.shoescms.domain.product.repository.ThuogHieuRepository;
import com.shoescms.domain.product.service.IThuongHieuService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class IThuongHieuServiceImpl implements IThuongHieuService {
    @Autowired
    ThuogHieuRepository thuogHieuRepository;
    @Override
    public Page<ThuongHieu> filterEntities(Pageable pageable, Specification<ThuongHieu> specification) {
        return thuogHieuRepository.findAll(specification,pageable);
    }

    @Override
    public ThuongHieuDTO add(ThuongHieuModel thuongHieuModel) {
       ThuongHieu thuongHieu = ThuongHieu.builder()
               .tenThuongHieu(thuongHieuModel.getTenThuongHieu())
               .slug(thuongHieuModel.getSlug())
               .build();
                thuogHieuRepository.save(thuongHieu);
       return ThuongHieuDTO.toDTO(thuongHieu);
    }

    @Override
    public ThuongHieuDTO update(ThuongHieuModel thuongHieuModel) {
       ThuongHieu th = thuogHieuRepository.findById(thuongHieuModel.getId()).orElse(null);
       if(th!=null){
           th.setTenThuongHieu(thuongHieuModel.getTenThuongHieu());
           th.setSlug(thuongHieuModel.getSlug());
           thuogHieuRepository.save(th);
       }
       return ThuongHieuDTO.toDTO(th);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            ThuongHieu entity= this.getById(id);
            this.thuogHieuRepository.delete(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<ThuongHieuDTO> getThuongHieus(String tenThuongHieu,String slug, Pageable pageable) {
        List<ThuongHieu> thuongHieu = thuogHieuRepository.findAll((Specification<ThuongHieu>) (root, query, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (!StringUtils.isEmpty(tenThuongHieu)) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("tenThuongHieu"), "%" + tenThuongHieu + "%"));
            }
            if (!StringUtils.isEmpty(slug)) {
                p = criteriaBuilder.and(p,criteriaBuilder.like(root.get("slug"),"%" + slug +"%"));
            }
            query.orderBy(criteriaBuilder.desc(root.get("tenThuongHieu")), criteriaBuilder.asc(root.get("id")));
            return p;
        }, pageable).getContent();
        return ThuongHieuDTO.convertToTDO(thuongHieu);
    }

    public ThuongHieu getById(Long id){
        return thuogHieuRepository.findById(id).orElseThrow(()-> new RuntimeException("22"));
    }

}
