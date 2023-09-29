package com.shoescms.domain.payment.services;

import com.shoescms.common.exception.CommonMessageException;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.exception.ProcessFailedException;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.repository.GioHangChiTietRepository;
import com.shoescms.domain.payment.dtos.*;
import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import com.shoescms.domain.payment.entities.DiaChiEntity;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IChiTietDonHangRepository;
import com.shoescms.domain.payment.repositories.IDiaChiRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.product.dto.SanPhamMetadataResDto;
import com.shoescms.domain.product.entitis.BienTheGiaTri;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.repository.IBienTheGiaTriRepository;
import com.shoescms.domain.product.repository.ISanPhamBienTheRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
//@Log4j
@RequiredArgsConstructor
public class PaymentService {

    private final IDonHangRepository donHangRepository;
    private final IChiTietDonHangRepository chiTietDonHangRepository;
    private final IDiaChiRepository diaChiRepository;
    private final GioHangChiTietRepository gioHangChiTietRepository;

    private final ISanPhamRepository sanPhamRepository;
    private final ISanPhamBienTheRepository sanPhamBienTheRepository;
    private final IBienTheGiaTriRepository bienTheGiaTriRepository;


    @Transactional
    public DonHangDto datHang(DatHangReqDto reqDto) {

        // luu thong tin don hang
        DonHangEntity donHangEntity = new DonHangEntity();
        //ma don hang
        donHangEntity.setMaDonHang(getRandomNumber(10));

        // luu thong tin chi tiet don hang
        if (reqDto.getNguoiTao() != null) // nguoi mua dang nhap
            taoChiTietDonHangTuGioHangChiTiet(reqDto.getGioHangItemIds(), donHangEntity);
        else // nguoi mua khong dang nhap
            taoChiTietDonHangTuGioHangTamThoi(reqDto.getGioHangTamThoiReqDto(), donHangEntity);

        donHangEntity.setPhuongThucTT(reqDto.getPhuongThucTT());
        donHangEntity.setTrangThai(ETrangThaiDonHang.WAITING_CONFIRM);

        donHangEntity.setTongGiaCuoiCung(donHangEntity.getTongGiaTien()); // need update later

        // luu dia chi dat hang
        DiaChiEntity diaChi = new DiaChiEntity();
        donHangEntity.setDiaChiEntity(diaChi);
        diaChi.setDiaChi(reqDto.getDiaChiNhanHang());
        diaChi.setSdt(reqDto.getSoDienThoaiNhanHang());
        diaChi.setTenNguoiNhan(reqDto.getHoTenNguoiNhan());

        diaChiRepository.saveAndFlush(diaChi);
        donHangRepository.saveAndFlush(donHangEntity);

        // xoa gio hang sau khi dat thanh cong
        // need update
        if (reqDto.getNguoiTao() != null)
            gioHangChiTietRepository.deleteAllById(reqDto.getGioHangItemIds());


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
        for (int i = 0; i < donHangEntity.getChiTietDonHangs().size(); i++) {
            ChiTietDonHangDto chiTietDonHangDto = new ChiTietDonHangDto();
            chiTietDonHangDto.setId(donHangEntity.getChiTietDonHangs().get(i).getId());

            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(donHangEntity.getChiTietDonHangs().get(i).getPhanLoaiSpId()).orElse(null);
            chiTietDonHangDto.setSanPham(SanPhamMetadataResDto.toDto(sanPhamBienThe.getSanPham()));
            chiTietDonHangDto.setPhanLoaiSpId(donHangEntity.getChiTietDonHangs().get(i).getPhanLoaiSpId());
            chiTietDonHangDto.setSoLuong(donHangEntity.getChiTietDonHangs().get(i).getSoLuong());
            chiTietDonHangDto.setGiaTien(donHangEntity.getChiTietDonHangs().get(i).getGiaTien());

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

    private void taoChiTietDonHangTuGioHangTamThoi(List<GioHangTamThoiReqDto> gioHangTamThoiReqDto, DonHangEntity donHangEntity) {
        List<ChiTietDonHangEntity> chiTietDonHangEntities = new ArrayList<>();
        BigDecimal tongTien = new BigDecimal(0);
        Integer tongSanPham = 0;

        for (int i = 0; i < gioHangTamThoiReqDto.size(); i++) {
            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(gioHangTamThoiReqDto.get(i).getSanPhamBienThe()).orElseThrow(() -> new ObjectNotFoundException(8));
            SanPham sanPham = sanPhamBienThe.getSanPham();
            BigDecimal tongTienSp = sanPham.getGiaMoi().multiply(BigDecimal.valueOf(gioHangTamThoiReqDto.get(i).getSoLuong().doubleValue()));
            tongTien = tongTien.add(tongTienSp);
            tongSanPham += gioHangTamThoiReqDto.get(i).getSoLuong();
            // tao thong tin
            ChiTietDonHangEntity chiTietDonHang = new ChiTietDonHangEntity();
            chiTietDonHang.setDonHang(donHangEntity);
            chiTietDonHang.setSoLuong(gioHangTamThoiReqDto.get(i).getSoLuong());
            chiTietDonHang.setGiaTien(sanPham.getGiaMoi());
            chiTietDonHang.setPhanLoaiSpId(sanPhamBienThe.getId());
            chiTietDonHang.setSanPhamId(sanPham.getId());
            chiTietDonHangEntities.add(chiTietDonHang);
        }

        donHangEntity.setTongSp(tongSanPham);
        donHangEntity.setTongTienSP(tongTien);
        donHangEntity.setTongGiaTien(tongTien);
        donHangEntity.setChiTietDonHangs(chiTietDonHangEntities);
    }

    private void taoChiTietDonHangTuGioHangChiTiet(List<Long> gioHangItems, DonHangEntity donHangEntity) {
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findAllById(gioHangItems);
        List<ChiTietDonHangEntity> chiTietDonHangEntities = new ArrayList<>();
        BigDecimal tongTien = new BigDecimal(0);
        Integer tongSanPham = 0;

        for (int i = 0; i < gioHangChiTiets.size(); i++) {
            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(gioHangChiTiets.get(i).getSanPhamBienThe()).orElseThrow(() -> new ObjectNotFoundException(8));
            SanPham sanPham = sanPhamBienThe.getSanPham();
            BigDecimal tongTienSp = sanPham.getGiaMoi().multiply(BigDecimal.valueOf(gioHangChiTiets.get(i).getSoLuong().doubleValue()));
            tongTien.add(tongTienSp);


            tongSanPham += gioHangChiTiets.get(i).getSoLuong();

            // tao thong tin
            ChiTietDonHangEntity chiTietDonHang = new ChiTietDonHangEntity();
            chiTietDonHang.setDonHang(donHangEntity);
            chiTietDonHang.setSoLuong(gioHangChiTiets.get(i).getSoLuong());
            chiTietDonHang.setGiaTien(sanPham.getGiaMoi());
            chiTietDonHang.setPhanLoaiSpId(sanPhamBienThe.getId());
            chiTietDonHang.setSanPhamId(sanPham.getId());

            chiTietDonHangEntities.add(chiTietDonHang);
        }

        donHangEntity.setTongSp(tongSanPham);
        donHangEntity.setTongGiaTien(tongTien);
        donHangEntity.setChiTietDonHangs(chiTietDonHangEntities);
    }

    public String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Long getDonHang(Long id) {
        return null;
    }

    public DonHangDto chiTietDonHang(Long id) {
        DonHangEntity donHangEntity = donHangRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(51));
        DonHangDto dto = DonHangDto.toDto(donHangEntity);
        dto.getChiTietDonHang().forEach(item -> {
            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(item.getPhanLoaiSpId()).orElseThrow(() -> new ProcessFailedException("failed"));
            item.setSanPham(SanPhamMetadataResDto.toDto(sanPhamBienThe.getSanPham()));
            if (sanPhamBienThe.getSanPham().getLoaiBienThe().equals(ELoaiBienThe.BOTH)) {
                StringBuilder stringBuilder = new StringBuilder();
                BienTheGiaTri bienTheGiaTri1 = bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri1()).orElse(null);
                BienTheGiaTri bienTheGiaTri2 = bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri2()).orElse(null);
                if (bienTheGiaTri1 != null)
                    stringBuilder.append("Màu: ").append(bienTheGiaTri1.getGiaTri());
                if (bienTheGiaTri2 != null)
                    stringBuilder.append("Size: ").append(bienTheGiaTri2.getGiaTri());
                item.setMotaPhanLoai(stringBuilder.toString());
            } else if (sanPhamBienThe.getSanPham().getLoaiBienThe().equals(ELoaiBienThe.COLOR))
                bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri1()).ifPresent(bienTheGiaTri1 -> item.setMotaPhanLoai("Màu: " + bienTheGiaTri1.getGiaTri()));
            else
                bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri2()).ifPresent(bienTheGiaTri2 -> item.setMotaPhanLoai("Size: " + bienTheGiaTri2.getGiaTri()));

        });
        return dto;
    }
}
