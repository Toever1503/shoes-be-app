package com.shoescms.domain.payment.services.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.exception.ProcessFailedException;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.domain.payment.dtos.*;
import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import com.shoescms.domain.payment.entities.DiaChiEntity;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IChiTietDonHangRepository;
import com.shoescms.domain.payment.repositories.IDiaChiRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.payment.services.IDonHangService;
import com.shoescms.domain.payment.services.PaymentService;
import com.shoescms.domain.product.dto.SanPhamMetadataResDto;
import com.shoescms.domain.product.entitis.BienTheGiaTri;
import com.shoescms.domain.product.entitis.SanPhamBienTheEntity;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.repository.IBienTheGiaTriRepository;
import com.shoescms.domain.product.repository.ISanPhamBienTheRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DonHangServiceImpl implements IDonHangService {
    private final ISanPhamBienTheRepository sanPhamBienTheRepository;
    private final IBienTheGiaTriRepository bienTheGiaTriRepository;
    private final FileRepository fileRepository;
    private final IDonHangRepository donHangRepository;
    private final IChiTietDonHangRepository chiTietDonHangRepository;
    private final ISanPhamRepository sanPhamRepository;
    private final IDiaChiRepository diaChiRepository;

    private final PaymentService paymentService;

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
            SanPhamBienTheEntity sanPhamBienTheEntity = sanPhamBienTheRepository.findById(item.getPhanLoaiSpId()).orElseThrow(() -> new ProcessFailedException("failed"));
            item.setSanPham(SanPhamMetadataResDto.toDto(sanPhamBienTheEntity.getSanPham()));
            item.getSanPham().setAnhChinh(fileRepository.findById(sanPhamBienTheEntity.getSanPham().getAnhChinh()).orElse(null));

            if (sanPhamBienTheEntity.getSanPham().getLoaiBienThe().equals(ELoaiBienThe.BOTH)) {
                StringBuilder stringBuilder = new StringBuilder();
                BienTheGiaTri bienTheGiaTri1 = bienTheGiaTriRepository.findById(sanPhamBienTheEntity.getBienTheGiaTri1()).orElse(null);
                BienTheGiaTri bienTheGiaTri2 = bienTheGiaTriRepository.findById(sanPhamBienTheEntity.getBienTheGiaTri2()).orElse(null);
                if (bienTheGiaTri1 != null)
                    stringBuilder.append("Màu: ").append(bienTheGiaTri1.getGiaTri());
                if (bienTheGiaTri2 != null)
                    stringBuilder.append(" , Size: ").append(bienTheGiaTri2.getGiaTri());
                item.setMotaPhanLoai(stringBuilder.toString());
            } else if (sanPhamBienTheEntity.getSanPham().getLoaiBienThe().equals(ELoaiBienThe.COLOR))
                bienTheGiaTriRepository.findById(sanPhamBienTheEntity.getBienTheGiaTri1()).ifPresent(bienTheGiaTri1 -> item.setMotaPhanLoai("Màu: " + bienTheGiaTri1.getGiaTri()));
            else
                bienTheGiaTriRepository.findById(sanPhamBienTheEntity.getBienTheGiaTri2()).ifPresent(bienTheGiaTri2 -> item.setMotaPhanLoai("Size: " + bienTheGiaTri2.getGiaTri()));
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

    @Override
    public DonHangDto themMoiDonHang(ThemMoiDonHangReqDto reqDto) {
        DonHangEntity donHangEntity = new DonHangEntity();
        donHangEntity.setMaDonHang(paymentService.getRandomNumber(10));
        donHangEntity.setPhuongThucTT(reqDto.getPhuongThucTT());

        AtomicReference<BigDecimal> tongTien = new AtomicReference<>(new BigDecimal(0));
        AtomicReference<Integer> tongSanPham = new AtomicReference<>(0);
        List<ChiTietDonHangEntity> chiTietDonHangEntities = reqDto.getPhanLoaidIds()
                .stream()
                .map(sp -> {
                    SanPhamBienTheEntity sanPhamBienTheEntity = sanPhamBienTheRepository.findById(sp.getSanPhamBienThe()).orElseThrow(() -> new ObjectNotFoundException(8));
                    tongTien.updateAndGet(v -> v.add(sanPhamBienTheEntity.getSanPham().getGiaMoi().multiply(BigDecimal.valueOf(sp.getSoLuong()))));
                    tongSanPham.updateAndGet(v -> v + sp.getSoLuong());
                    return ChiTietDonHangEntity
                            .builder()
                            .phanLoaiSpId(sp.getSanPhamBienThe())
                            .soLuong(sp.getSoLuong())
                            .giaTien(sanPhamBienTheEntity.getSanPham().getGiaMoi())
                            .spId(sanPhamBienTheEntity.getSanPham().getId())
                            .build();
                })
                .toList();

        if (reqDto.getNguoiDat() != null)
            donHangEntity.setNguoiMuaId(reqDto.getNguoiDat());
        if (reqDto.getNguoiTao() != null)
            donHangEntity.setNguoiCapNhat(reqDto.getNguoiTao());

        donHangEntity.setGhiChu(reqDto.getGhiChu());
        donHangEntity.setTongSp(tongSanPham.get());
        donHangEntity.setTongGiaTien(tongTien.get());
        donHangEntity.setTrangThai(ETrangThaiDonHang.WAITING_CONFIRM);
        donHangEntity.setTongGiaCuoiCung(donHangEntity.getTongGiaTien()); // need update later

        DiaChiEntity diaChi = new DiaChiEntity();
        donHangEntity.setDiaChiEntity(diaChi);
        diaChi.setDiaChi(reqDto.getDiaChiNhanHang());
        diaChi.setSdt(reqDto.getSoDienThoaiNhanHang());
        diaChi.setTenNguoiNhan(reqDto.getHoTenNguoiNhan());

        diaChiRepository.saveAndFlush(diaChi);
        donHangRepository.saveAndFlush(donHangEntity);
        chiTietDonHangEntities.forEach(i -> i.setDonHang(donHangEntity.getId()));
        chiTietDonHangRepository.saveAllAndFlush(chiTietDonHangEntities);


        DonHangDto donHangDto = new DonHangDto();
        donHangDto.setId(donHangEntity.getId());
        donHangDto.setMaDonHang(donHangEntity.getMaDonHang());
        donHangDto.setTongSp(donHangEntity.getTongSp());
        donHangDto.setPhuongThucTT(donHangEntity.getPhuongThucTT());
        donHangDto.setNguoiMuaId(donHangEntity.getNguoiMuaId());
        donHangDto.setTrangThai(donHangEntity.getTrangThai());
        donHangDto.setNgayTao(donHangEntity.getNgayTao());
        donHangDto.setTongGiaCuoiCung(donHangEntity.getTongGiaTien());

        List<ChiTietDonHangDto> chiTietDonHangDtos = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : chiTietDonHangEntities) {
            ChiTietDonHangDto chiTietDonHangDto = new ChiTietDonHangDto();
            chiTietDonHangDto.setId(chiTietDonHangEntity.getId());

            SanPhamBienTheEntity sanPhamBienTheEntity = sanPhamBienTheRepository.findById(chiTietDonHangEntity.getPhanLoaiSpId()).orElse(null);
            chiTietDonHangDto.setSanPham(SanPhamMetadataResDto.toDto(sanPhamBienTheEntity.getSanPham()));
            chiTietDonHangDto.setPhanLoaiSpId(chiTietDonHangEntity.getPhanLoaiSpId());
            chiTietDonHangDto.setSoLuong(chiTietDonHangEntity.getSoLuong());
            chiTietDonHangDto.setGiaTien(chiTietDonHangEntity.getGiaTien());
            chiTietDonHangDtos.add(chiTietDonHangDto);
        }

        DiaChiDto diaChiDto = new DiaChiDto();
        diaChiDto.setId(diaChi.getId());
        diaChiDto.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        diaChiDto.setSdt(diaChi.getSdt());
        diaChiDto.setDiaChi(diaChi.getDiaChi());
        donHangDto.setDiaChi(diaChiDto);
        donHangDto.setChiTietDonHang(chiTietDonHangDtos);


        return donHangDto;
    }

    @Override
    public Page<DonHangEntity> findByNguoiMuaId(Long nguoiMuaId,ETrangThaiDonHang trangThai, Pageable pageable) {
        return donHangRepository.findByNguoiMuaId(nguoiMuaId, trangThai, pageable);
    }

    @Override
    public Page<DonHangEntity> findByNguoiMuaId(Long nguoiMuaId, Pageable pageable  ) {
        return donHangRepository.findByNguoiMuaId(nguoiMuaId, pageable);
    }


}
