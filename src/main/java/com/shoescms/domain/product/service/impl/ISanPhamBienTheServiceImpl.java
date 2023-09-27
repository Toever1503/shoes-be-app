package com.shoescms.domain.product.service.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.model.FileEntity;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.domain.product.dto.BienTheGiaTriDTO;
import com.shoescms.domain.product.dto.SanPhamBienTheDTO;
import com.shoescms.domain.product.entitis.*;
import com.shoescms.domain.product.models.SanPhamBienTheModel;
import com.shoescms.domain.product.repository.BienTheGiaTriRepository;
import com.shoescms.domain.product.repository.BienTheRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.repository.SanPhamBienTheRepository;
import com.shoescms.domain.product.service.SanPhamBienTheService;
import com.shoescms.domain.product.entitis.BienTheGiaTri_;
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
    private final FileRepository fileRepository;

    public ISanPhamBienTheServiceImpl(BienTheRepository bienTheRepository, BienTheGiaTriRepository bienTheGiaTriRepository, SanPhamBienTheRepository sanPhamBienTheRepository, ISanPhamRepository sanPhamRepository, FileRepository fileRepository) {
        this.bienTheRepository = bienTheRepository;
        this.bienTheGiaTriRepository = bienTheGiaTriRepository;
        this.sanPhamBienTheRepository = sanPhamBienTheRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.fileRepository = fileRepository;

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
        if (bienThe.getTenBienThe().equals("COLOR")) {
            bienTheGiaTriRepository.saveAllAndFlush(List.of(
                    BienTheGiaTri
                            .builder()
                            .id(1L)
                            .bienThe(bienThe)
                            .giaTri("Đỏ")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(2L)
                            .bienThe(bienThe)
                            .giaTri("Xanh")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(3L)
                            .bienThe(bienThe)
                            .giaTri("Vàng")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(4L)
                            .bienThe(bienThe)
                            .giaTri("Trắng")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(5L)
                            .bienThe(bienThe)
                            .giaTri("Xám")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(6L)
                            .bienThe(bienThe)
                            .giaTri("Nâu")
                            .build()
            ));
        } else {
            bienTheGiaTriRepository.saveAllAndFlush(List.of(
                    BienTheGiaTri
                            .builder()
                            .id(46L)
                            .bienThe(bienThe)
                            .giaTri("36")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(47L)
                            .bienThe(bienThe)
                            .giaTri("37")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(48L)
                            .bienThe(bienThe)
                            .giaTri("38")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(49L)
                            .bienThe(bienThe)
                            .giaTri("39")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(50L)
                            .bienThe(bienThe)
                            .giaTri("40")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(51L)
                            .bienThe(bienThe)
                            .giaTri("41")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(52L)
                            .bienThe(bienThe)
                            .giaTri("42")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(53L)
                            .bienThe(bienThe)
                            .giaTri("43")
                            .build(),
                    BienTheGiaTri
                            .builder()
                            .id(54L)
                            .bienThe(bienThe)
                            .giaTri("44")
                            .build()
            ));
        }
    }

    @Override
    public Page<SanPhamBienTheDTO> filterEntities(Pageable pageable, Specification<SanPhamBienThe> specification) {
        Page<SanPhamBienThe> sanPhamBienThes = sanPhamBienTheRepository.findAll(specification, pageable);
        return sanPhamBienThes.map(SanPhamBienTheDTO::toDTO);

    }

    @Override
    @Transactional
    synchronized public SanPhamBienTheDTO add(SanPhamBienTheModel model) {
        checkValue(model);
        SanPham sanPham = sanPhamRepository.findById(model.getSanPham()).orElse(null);
        if (sanPham == null)
            throw new ObjectNotFoundException(8);

        FileEntity file = fileRepository.findById(model.getAnh()).orElse(null);
        if (file != null) {
            file.setIsVerified(true);
            fileRepository.saveAndFlush(file);
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
        return SanPhamBienTheDTO
                .toDTO(sanPhamBienThe)
                .setAnh(fileRepository.findById(sanPhamBienThe.getAnh()).orElse(null), null);
    }

    public void checkValue(SanPhamBienTheModel model) {
        if (model.getBienThe1() != null && bienTheRepository.findById(model.getBienThe1()).isEmpty()) {
            throw new ObjectNotFoundException(6);
        }
        if (model.getBienThe2() != null && bienTheRepository.findById(model.getBienThe2()).isEmpty()) {
            throw new ObjectNotFoundException(6);
        }
        if (model.getBienTheGiaTri1() != null && bienTheGiaTriRepository.findById(model.getBienTheGiaTri1()).isEmpty()) {
            throw new ObjectNotFoundException(7);
        }
        if (model.getBienTheGiaTri2() != null && bienTheGiaTriRepository.findById(model.getBienTheGiaTri2()).isEmpty()) {
            throw new ObjectNotFoundException(7);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            SanPhamBienThe sanPhamBienThe = this.getByiD(id);
            sanPhamBienThe.delete();
            this.sanPhamBienTheRepository.saveAndFlush(sanPhamBienThe);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public List<SanPhamBienTheDTO> saveAllStep2(List<SanPhamBienTheModel> models) {
        return models.stream().map(this::add).toList();
    }

    @Override
    public List<BienTheGiaTriDTO> getListBienTheGiaTriByBienTheId(Long bienTheId) {
        return bienTheGiaTriRepository
                .findAll(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BienTheGiaTri_.BIEN_THE), bienTheId)))
                .stream()
                .map(BienTheGiaTriDTO::toDto)
                .toList();
    }

    @Override
    public List<SanPhamBienTheDTO> findAllPhanLoaiTheoSanPham(Long id) {
        return sanPhamBienTheRepository.findAllAllBySanPhamIdAndNgayXoaIsNull(id)
                .stream()
                .map(item ->
                        SanPhamBienTheDTO.toDTO(item)
                                .setAnh(fileRepository.findById(item.getAnh()).orElse(null), fileRepository.findById(item.getSanPham().getAnhChinh()).get())
                                .setGiaTriObj1(bienTheGiaTriRepository.findById(item.getBienTheGiaTri1() == null ? 0 : item.getBienTheGiaTri1()).orElse(null))
                                .setGiaTriObj2(bienTheGiaTriRepository.findById(item.getBienTheGiaTri2() == null ? 0 : item.getBienTheGiaTri2()).orElse(null))
                )
                .toList();
    }

    @Override
    public void capNhatSoLuongSanPhamChoBienThe(Long id, int soLuong) {
        SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(1));
        sanPhamBienThe.setSoLuong(soLuong);
        sanPhamBienTheRepository.saveAndFlush(sanPhamBienThe);
    }

    public SanPhamBienThe getByiD(Long id) {
        return sanPhamBienTheRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(0));
    }
}
