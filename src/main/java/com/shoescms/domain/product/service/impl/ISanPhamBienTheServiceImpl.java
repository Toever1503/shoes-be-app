package com.shoescms.domain.product.service.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.product.dto.BienTheGiaTriDTO;
import com.shoescms.domain.product.dto.SanPhamBienTheDTO;
import com.shoescms.domain.product.entitis.*;
import com.shoescms.domain.product.models.SanPhamBienTheModel;
import com.shoescms.domain.product.repository.BienTheGiaTriRepository;
import com.shoescms.domain.product.repository.BienTheRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.repository.SanPhamBienTheRepository;
import com.shoescms.domain.product.service.SanPhamBienTheService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ISanPhamBienTheServiceImpl implements SanPhamBienTheService {

    private final BienTheRepository bienTheRepository;
    private final BienTheGiaTriRepository bienTheGiaTriRepository;
    private final SanPhamBienTheRepository sanPhamBienTheRepository;
    private final ISanPhamRepository sanPhamRepository;

    public ISanPhamBienTheServiceImpl(BienTheRepository bienTheRepository, BienTheGiaTriRepository bienTheGiaTriRepository, SanPhamBienTheRepository sanPhamBienTheRepository, ISanPhamRepository sanPhamRepository) {
        this.bienTheRepository = bienTheRepository;
        this.bienTheGiaTriRepository = bienTheGiaTriRepository;
        this.sanPhamBienTheRepository = sanPhamBienTheRepository;
        this.sanPhamRepository = sanPhamRepository;

        initBienThe();
    }

    private void initBienThe() {
        BienThe bienTheMau = bienTheRepository.saveAndFlush(BienThe
                .builder()
                        .id(1L)
                        .tenBienThe("COLOR")
                .build());
        BienThe bienTheSize = bienTheRepository.saveAndFlush(BienThe
                .builder()
                .id(2L)
                .tenBienThe("SIZE")
                .build());
        initGiaTriBienThe(bienTheMau);
        initGiaTriBienThe(bienTheSize);
    }

    private void initGiaTriBienThe(BienThe bienThe) {
        if(bienThe.getTenBienThe().equals("COLOR"))
        {
            bienTheGiaTriRepository.saveAllAndFlush(List.of(
                    BienTheGiaTri
                            .builder()
                            .id(1L)
                            .bienThe(bienThe.getId())
                            .giaTri("RED")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(2L)
                            .bienThe(bienThe.getId())
                            .giaTri("BLUE")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(3L)
                            .bienThe(bienThe.getId())
                            .giaTri("YELLOW")
                            .build()
            ));
        }
        else{
            bienTheGiaTriRepository.saveAllAndFlush(List.of(
                    BienTheGiaTri
                            .builder()
                            .id(50L)
                            .bienThe(bienThe.getId())
                            .giaTri("40")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(51L)
                            .bienThe(bienThe.getId())
                            .giaTri("41")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(52L)
                            .bienThe(bienThe.getId())
                            .giaTri("42")
                            .build()
            ));
        }
    }

    @Override
    public Page<SanPhamBienTheDTO> filterEntities(Pageable pageable, Specification<SanPhamBienThe> specification) {
        Page<SanPhamBienThe> sanPhamBienThes = sanPhamBienTheRepository.findAll(specification,pageable);
        return sanPhamBienThes.map(SanPhamBienTheDTO::toDTO);

    }

    @Override
    @Transactional
    synchronized public SanPhamBienTheDTO add(SanPhamBienTheModel model) {
            checkValue(model);
        SanPham sanPham = sanPhamRepository.findById(model.getSanPham()).orElse(null);
        if(sanPham == null)
            throw new ObjectNotFoundException(8);

        if(sanPham.getLoaiBienThe() == null){
            sanPham.setLoaiBienThe(model.getLoaiBienThe());
            sanPhamRepository.saveAndFlush(sanPham);
        }
        else if(!sanPham.getLoaiBienThe().equals(model.getLoaiBienThe())){
            sanPhamBienTheRepository.deleteAllBySanPhamId(sanPham.getId());
            model.setId(null);
        }

        SanPhamBienThe sanPhamBienThe = SanPhamBienThe.builder()
                .id(model.getId())
                .bienThe1(model.getBienThe1())
                .bienThe2(model.getBienThe2())
                .sanPham(sanPham)
                .bienTheGiaTri1(model.getBienTheGiaTri1())
                .bienTheGiaTri2(model.getBienTheGiaTri2())
                .anh(model.getAnh())
                .build();
                this.sanPhamBienTheRepository.saveAndFlush(sanPhamBienThe);
        return SanPhamBienTheDTO.toDTO(sanPhamBienThe);
    }

    public void checkValue(SanPhamBienTheModel model){
        if(model.getBienThe1() !=null && bienTheRepository.findById(model.getBienThe1()).isEmpty()) {
            throw new ObjectNotFoundException(6);
        }
        if(model.getBienThe2() !=null && bienTheRepository.findById(model.getBienThe2()).isEmpty()) {
            throw new ObjectNotFoundException(6);
        }
        if(model.getBienTheGiaTri1() !=null && bienTheGiaTriRepository.findById(model.getBienTheGiaTri1()).isEmpty()) {
            throw new ObjectNotFoundException(7);
        }
        if(model.getBienTheGiaTri2() !=null && bienTheGiaTriRepository.findById(model.getBienTheGiaTri2()).isEmpty()) {
            throw new ObjectNotFoundException(7);
        }
    }


    @Override
    public SanPhamBienTheDTO update(SanPhamBienTheModel sanPhamBienTheModel) {
        checkValue(sanPhamBienTheModel);
        SanPhamBienThe sp = sanPhamBienTheRepository.findById(sanPhamBienTheModel.getId()).orElse(null);
       if(sp!=null){
//           sp.setSanPham(sanPhamBienTheModel.getSanPham());
           sp.setBienTheGiaTri1(sanPhamBienTheModel.getBienTheGiaTri1());
           sp.setBienTheGiaTri2(sanPhamBienTheModel.getBienTheGiaTri2());
           sp.setBienThe1(sanPhamBienTheModel.getBienThe1());
           sp.setBienThe2(sanPhamBienTheModel.getBienThe2());
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

    @Override
    @Transactional
    public SanPhamBienTheDTO saveAllStep2(List<SanPhamBienTheModel> models) {
        return null;
    }

    @Override
    public List<BienTheGiaTriDTO> getListBienTheGiaTriByBienTheId(Long bienTheId) {
        return bienTheGiaTriRepository
                .findAll(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BienTheGiaTri_.BIEN_THE), bienTheId)))
                .stream().map(BienTheGiaTriDTO::toDto).toList();
    }

    public  SanPhamBienThe getByiD(Long id){
        return sanPhamBienTheRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(0));
    }
}
