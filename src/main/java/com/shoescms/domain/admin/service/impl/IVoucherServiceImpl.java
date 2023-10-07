package com.shoescms.domain.admin.service.impl;

import com.shoescms.domain.admin.dto.VoucherDTO;
import com.shoescms.domain.admin.entitis.Voucher;
import com.shoescms.domain.admin.models.VoucherModel;
import com.shoescms.domain.admin.repository.VoucherRepository;
import com.shoescms.domain.admin.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class IVoucherServiceImpl implements IVoucherService {
    @Autowired
    @Lazy
    VoucherRepository voucherRepository;

    @Override
    public Page<Voucher> filterEntities(Pageable pageable, Specification<Voucher> specification){
        return this.voucherRepository.findAll(specification,pageable);
    }

    @Override
    public VoucherDTO add(VoucherModel voucherModel){
        Voucher voucher = Voucher.builder()
                .maVoucher(voucherModel.getMaVoucher())
                .denNgay(voucherModel.getDenNgay())
                .soLuong(voucherModel.getSoLuong())
                .menhGia(voucherModel.getMenhGia())
                .tuNgay(voucherModel.getTuNgay())
                .trangThai(voucherModel.getTrangThai())
                .build();
        this.voucherRepository.saveAndFlush(voucher);
        return VoucherDTO.toDTO(voucher);
    }

    @Override
    public VoucherDTO update(VoucherModel voucherModel){
        Voucher voucher = this.voucherRepository.findById(voucherModel.getId()).orElseThrow(null);
        if(!"".equals(voucher)){
            voucher.setMaVoucher(voucherModel.getMaVoucher());
            voucher.setTuNgay(voucherModel.getTuNgay());
            voucher.setDenNgay(voucherModel.getDenNgay());
            voucher.setSoLuong(voucherModel.getSoLuong());
            voucher.setMenhGia(voucherModel.getMenhGia());
            voucher.setTrangThai(voucherModel.getTrangThai());
            this.voucherRepository.saveAndFlush(voucher);
        }
        return VoucherDTO.toDTO(voucher);
    }

    @Override
    public boolean deleteById(Long id){
        try {
            Voucher entity = this.getById(id);
            this.voucherRepository.delete(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Voucher getById(Long id){
        return voucherRepository.findById(id).orElseThrow(() ->new RuntimeException("22"));
    }

}
