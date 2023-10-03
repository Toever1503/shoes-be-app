package com.shoescms.domain.payment.services.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.exception.ProcessFailedException;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.domain.payment.dtos.DonHangDto;
import com.shoescms.domain.payment.dtos.ETrangThaiDonHang;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IChiTietDonHangRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.payment.services.IDonHangService;
import com.shoescms.domain.product.dto.SanPhamMetadataResDto;
import com.shoescms.domain.product.entitis.BienTheGiaTri;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.repository.IBienTheGiaTriRepository;
import com.shoescms.domain.product.repository.ISanPhamBienTheRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonHangServiceImpl implements IDonHangService {
    private final ISanPhamBienTheRepository sanPhamBienTheRepository;
    private final IBienTheGiaTriRepository bienTheGiaTriRepository;
    private final FileRepository fileRepository;
    @Autowired
    private IDonHangRepository donHangRepository;

    @Override
    public Page<DonHangDto> filterEntities(Pageable pageable, Specification<DonHangEntity> specification) {
        Page<DonHangEntity> donHangDtoPage = donHangRepository.findAll(specification, pageable);
        return donHangDtoPage.map(item -> DonHangDto.toDto(item));
    }

    @Override
    public DonHangDto chiTietDonHang(Long id) {
        DonHangEntity donHangEntity = donHangRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(51));
        DonHangDto dto = DonHangDto.toDto(donHangEntity);
        dto.getChiTietDonHang().forEach(item -> {
            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(item.getPhanLoaiSpId()).orElseThrow(() -> new ProcessFailedException("failed"));
            item.setSanPham(SanPhamMetadataResDto.toDto(sanPhamBienThe.getSanPham()));
            item.getSanPham().setAnhChinh(fileRepository.findById(sanPhamBienThe.getSanPham().getAnhChinh()).orElse(null));

            if (sanPhamBienThe.getSanPham().getLoaiBienThe().equals(ELoaiBienThe.BOTH)) {
                StringBuilder stringBuilder = new StringBuilder();
                BienTheGiaTri bienTheGiaTri1 = bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri1()).orElse(null);
                BienTheGiaTri bienTheGiaTri2 = bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri2()).orElse(null);
                if (bienTheGiaTri1 != null)
                    stringBuilder.append("Màu: ").append(bienTheGiaTri1.getGiaTri());
                if (bienTheGiaTri2 != null)
                    stringBuilder.append(" , Size: ").append(bienTheGiaTri2.getGiaTri());
                item.setMotaPhanLoai(stringBuilder.toString());
            } else if (sanPhamBienThe.getSanPham().getLoaiBienThe().equals(ELoaiBienThe.COLOR))
                bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri1()).ifPresent(bienTheGiaTri1 -> item.setMotaPhanLoai("Màu: " + bienTheGiaTri1.getGiaTri()));
            else
                bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri2()).ifPresent(bienTheGiaTri2 -> item.setMotaPhanLoai("Size: " + bienTheGiaTri2.getGiaTri()));
        });
        return dto;
    }

    @Override
    public void capNhatTrangThai(Long id, ETrangThaiDonHang trangThai, Long userId) {
        DonHangEntity donHangEntity = donHangRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(51));
        donHangEntity.setTrangThai(trangThai);
        donHangEntity.setNguoiCapNhat(userId);
        donHangRepository.saveAndFlush(donHangEntity);
    }

}
