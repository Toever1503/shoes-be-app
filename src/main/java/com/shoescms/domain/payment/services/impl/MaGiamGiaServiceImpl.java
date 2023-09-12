package com.shoescms.domain.payment.services.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.payment.dtos.MaGiamGiaDto;
import com.shoescms.domain.payment.entities.MaGiamGiaEntity;
import com.shoescms.domain.payment.model.MaGiamGiaModel;
import com.shoescms.domain.payment.repositories.IMaGiamGiaRepository;
import com.shoescms.domain.payment.services.IMaGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MaGiamGiaServiceImpl implements IMaGiamGiaService {

    @Autowired
    private IMaGiamGiaRepository maGiamGiaRepository;

    @Override
    public Page<MaGiamGiaEntity> filterEntities(Pageable pageable, Specification<MaGiamGiaEntity> specification) {
        return this.maGiamGiaRepository.findAll(specification, pageable);
    }

    @Override
    public MaGiamGiaDto add(MaGiamGiaModel model) {
        MaGiamGiaEntity entity = MaGiamGiaEntity.builder()
                .maCode(model.getMaCode())
                .ngayBatDau(model.getNgayBatDau())
                .ngayKetThuc(model.getNgayKetThuc())
                .nguoiTao(model.getNguoiTao())
                .nguoiCapNhat(model.getNguoiCapNhat())
                .soLuong(model.getSoLuong())
                .soTien(model.getSoTien())
                .ngayXoa(model.getNgayXoa())
                .soTien(model.getSoTien())
                .ngayTao(model.getNgayTao())
                .soTienToiThieu(model.getSoTienToiThieu())
                .build();
        this.maGiamGiaRepository.saveAndFlush(entity);
        return MaGiamGiaDto.toDto(entity);
    }

    @Override
    public MaGiamGiaDto update(MaGiamGiaModel model) {
        MaGiamGiaEntity entity = this.getById(model.getId());
        entity.setMaCode(model.getMaCode());
        entity.setSoLuong(model.getSoLuong());
        entity.setSoTien(model.getSoTien());
        entity.setSoTienToiThieu(model.getSoTienToiThieu());
        entity.setNgayBatDau(model.getNgayBatDau());
        entity.setNgayKetThuc(model.getNgayKetThuc());
        entity.setNguoiTao(model.getNguoiTao());
        entity.setNguoiCapNhat(model.getNguoiCapNhat());
        entity.setNgayTao(model.getNgayTao());
        entity.setNgayCapNhat(model.getNgayCapNhat());
        entity.setNgayXoa(model.getNgayXoa());
        this.maGiamGiaRepository.saveAndFlush(entity);
        return MaGiamGiaDto.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        maGiamGiaRepository.deleteById(id);
    }

    @Override
    public MaGiamGiaEntity getById(Long id) {
        return this.maGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
    }

    @Override
    public MaGiamGiaEntity findByMaCode(String maCode) {
        return this.maGiamGiaRepository.findByMaCode(maCode).orElseThrow(() -> new ObjectNotFoundException("2"));
    }
}
