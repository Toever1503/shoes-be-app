package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.MaGiamGiaDto;
import com.shoescms.domain.payment.entities.MaGiamGiaEntity;
import com.shoescms.domain.payment.model.MaGiamGiaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;


public interface IMaGiamGiaService {

    Page<MaGiamGiaEntity> filterEntities(Pageable pageable, Specification<MaGiamGiaEntity> specification);
    MaGiamGiaDto add(MaGiamGiaModel model);

    MaGiamGiaDto update(MaGiamGiaModel model);

    void delete(Long id);

    MaGiamGiaEntity getById(Long id);

    MaGiamGiaEntity findByMaCode(String maCode);

    MaGiamGiaEntity findByActiveInActive();
}
