package com.shoescms.domain.shoes.service.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.shoes.dto.SanPhamBienTheDTO;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import com.shoescms.domain.shoes.models.SanPhamBienTheModel;
import com.shoescms.domain.shoes.repository.BienTheGiaTriRepository;
import com.shoescms.domain.shoes.repository.BienTheRepository;
import com.shoescms.domain.shoes.repository.ISanPhamRepository;
import com.shoescms.domain.shoes.repository.SanPhamBienTheRepository;
import com.shoescms.domain.shoes.service.SanPhamBienTheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ISanPhamBienTheServiceImpl implements SanPhamBienTheService {

    @Lazy
    @Autowired
    BienTheRepository bienTheRepository;
    @Autowired
    BienTheGiaTriRepository bienTheGiaTriRepository;
    @Autowired
    SanPhamBienTheRepository sanPhamBienTheRepository;
    @Autowired
    ISanPhamRepository sanPhamRepository;

    @Override
    public Page<SanPhamBienTheDTO> filterEntities(Pageable pageable, Specification<SanPhamBienThe> specification) {
        Page<SanPhamBienThe> sanPhamBienThes = sanPhamBienTheRepository.findAll(specification,pageable);
        return sanPhamBienThes.map(SanPhamBienTheDTO::toDTO);

    }

    @Override
    public SanPhamBienTheDTO add(SanPhamBienTheModel sanPhamBienTheModel) {
            checkValue(sanPhamBienTheModel);
        SanPhamBienThe sanPhamBienThe = SanPhamBienThe.builder()
                .bienThe(sanPhamBienTheModel.getBienThe())
                .sanPham(sanPhamBienTheModel.getSanPham())
                .bienTheGiaTri(sanPhamBienTheModel.getBienTheGiaTri())
                .anh(sanPhamBienTheModel.getAnh())
                .ngayXoa(sanPhamBienTheModel.getNgayXoa())
                .soLuong(sanPhamBienTheModel.getSoLuong())
                .build();
                this.sanPhamBienTheRepository.saveAndFlush(sanPhamBienThe);
        return SanPhamBienTheDTO.toDTO(sanPhamBienThe);
    }

    public void checkValue(SanPhamBienTheModel model){
        if(model.getBienThe().getId()==null || bienTheRepository.findById(model.getBienThe().getId()).isEmpty()) {
            throw new ObjectNotFoundException(1);
        }
        if(model.getBienTheGiaTri().getId()==null || bienTheGiaTriRepository.findById(model.getBienTheGiaTri().getId()).isEmpty()) {
            throw new ObjectNotFoundException(2);
        }
        if(model.getSanPham().getId()==null || sanPhamRepository.findById(model.getSanPham().getId()).isEmpty()) {
            throw new ObjectNotFoundException(3);
        }
    }


    @Override
    public SanPhamBienTheDTO update(SanPhamBienTheModel sanPhamBienTheModel) {
        checkValue(sanPhamBienTheModel);
        SanPhamBienThe sp = sanPhamBienTheRepository.findById(sanPhamBienTheModel.getId()).orElse(null);
       if(sp!=null){
           sp.setSanPham(sanPhamBienTheModel.getSanPham());
           sp.setBienTheGiaTri(sanPhamBienTheModel.getBienTheGiaTri());
           sp.setBienThe(sanPhamBienTheModel.getBienThe());
           sp.setAnh(sanPhamBienTheModel.getAnh());
           sp.setSoLuong(sanPhamBienTheModel.getSoLuong());
           sp.setNgayXoa(sanPhamBienTheModel.getNgayXoa());
           sanPhamBienTheRepository.saveAndFlush(sp);
       }
        return SanPhamBienTheDTO.toDTO(sp);

    }

    @Override
    public boolean deleteById(Long id) {
        try {
            SanPhamBienThe sanPhamBienThe = this.getByiD(id);
            this.sanPhamBienTheRepository.delete(sanPhamBienThe);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public  SanPhamBienThe getByiD(Long id){
        return sanPhamBienTheRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(0));
    }
}
