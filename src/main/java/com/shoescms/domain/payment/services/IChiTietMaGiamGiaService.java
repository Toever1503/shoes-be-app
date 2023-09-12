package com.shoescms.domain.payment.services;

import com.shoescms.domain.payment.dtos.ChiTietMaGiamGiaDto;
import com.shoescms.domain.payment.entities.ChiTietMaGiamGiaEntity;
import com.shoescms.domain.payment.model.ChiTietMaGiamGiaModel;

public interface IChiTietMaGiamGiaService {

    ChiTietMaGiamGiaDto add(ChiTietMaGiamGiaModel model);

    ChiTietMaGiamGiaDto update(ChiTietMaGiamGiaModel model);

    void delete(Long id);

    ChiTietMaGiamGiaEntity getById(Long id);


}
