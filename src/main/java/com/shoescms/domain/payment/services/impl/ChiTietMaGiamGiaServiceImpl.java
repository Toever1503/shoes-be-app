package com.shoescms.domain.payment.services.impl;


import com.shoescms.domain.payment.dtos.ChiTietMaGiamGiaDto;
import com.shoescms.domain.payment.entities.ChiTietMaGiamGiaEntity;
import com.shoescms.domain.payment.model.ChiTietMaGiamGiaModel;
import com.shoescms.domain.payment.repositories.IChiTietMaGiamGiaRepository;
import com.shoescms.domain.payment.services.IChiTietMaGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChiTietMaGiamGiaServiceImpl implements IChiTietMaGiamGiaService {

    @Autowired
    private IChiTietMaGiamGiaRepository chiTietMaGiamGiaRepository;

    @Override
    public ChiTietMaGiamGiaDto add(ChiTietMaGiamGiaModel model) {
        ChiTietMaGiamGiaEntity entity = ChiTietMaGiamGiaEntity.builder()
                .maGiamGiaId(model.getMaGiamGiaId())
                .donHangId(model.getDonHangId())
                .ngaySuDung(model.getNgaySuDung())
                .build();
        this.chiTietMaGiamGiaRepository.saveAndFlush(entity);
        return ChiTietMaGiamGiaDto.toDto(entity);
    }

    @Override
    public ChiTietMaGiamGiaDto update(ChiTietMaGiamGiaModel model) {
        ChiTietMaGiamGiaEntity entity = this.getById(model.getId());
        entity.setMaGiamGiaId(model.getMaGiamGiaId());
        entity.setDonHangId(model.getDonHangId());
        entity.setNgaySuDung(model.getNgaySuDung());
        this.chiTietMaGiamGiaRepository.saveAndFlush(entity);
        return ChiTietMaGiamGiaDto.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        this.chiTietMaGiamGiaRepository.deleteById(id);
    }

    @Override
    public ChiTietMaGiamGiaEntity getById(Long id) {
        return this.chiTietMaGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
    }
}
