package com.shoescms.domain.payment.services;

import com.shoescms.common.exception.CommonMessageException;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.repository.GioHangChiTietRepository;
import com.shoescms.domain.payment.dtos.ChiTietDonHangDto;
import com.shoescms.domain.payment.dtos.DatHangReqDto;
import com.shoescms.domain.payment.dtos.DiaChiDto;
import com.shoescms.domain.payment.dtos.DonHangDto;
import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import com.shoescms.domain.payment.entities.DiaChiEntity;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IChiTietDonHangRepository;
import com.shoescms.domain.payment.repositories.IDiaChiRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.product.entitis.SanPham;
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


    @Transactional
    public DonHangDto datHang(DatHangReqDto reqDto) {
        // lay ra thong tin cac san pham dat
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findAllById(reqDto.getGioHangItemIds());

        // luu thong tin don hang
        DonHangEntity donHangEntity = new DonHangEntity();
        //ma don hang
        donHangEntity.setMaDonHang(getRandomNumber(30));

        BigDecimal tongTien = new BigDecimal(0);
        Integer tongSanPham = 0;
        // luu thong tin chi tiet don hang
        List<ChiTietDonHangEntity> chiTietDonHangEntities = new ArrayList<>();
        for (int i = 0; i < gioHangChiTiets.size(); i++) {
            SanPham sanPham = sanPhamRepository.findById(gioHangChiTiets.get(i).getSanPham()).orElseThrow(() -> new ObjectNotFoundException(8));

            BigDecimal tongTienSp = sanPham.getGiaMoi().multiply(BigDecimal.valueOf(gioHangChiTiets.get(i).getSoLuong().doubleValue()));
            tongTien.add(tongTienSp);

            tongSanPham += gioHangChiTiets.get(i).getSoLuong();

            Long sanPhamBienThe = gioHangChiTiets.get(i).getSanPhamBienThe();

            // tao thong tin
            ChiTietDonHangEntity chiTietDonHang = new ChiTietDonHangEntity();
            chiTietDonHang.setDonHang(donHangEntity);
            chiTietDonHang.setSoLuong(gioHangChiTiets.get(i).getSoLuong());
            chiTietDonHang.setGiaTien(sanPham.getGiaMoi());
            chiTietDonHang.setPhanLoaiSpId(sanPhamBienThe);
            chiTietDonHang.setSanPhamId(sanPham.getId());


            chiTietDonHangEntities.add(chiTietDonHang);
        }
        donHangEntity.setChiTietDonHangs(chiTietDonHangEntities);
        donHangEntity.setTongSp(tongSanPham);
        donHangEntity.setPhuongThucTT(reqDto.getPhuongThucTT());
        donHangEntity.setTongGiaTien(tongTien);
        donHangEntity.setTongGiaCuoiCung(tongTien);
        donHangEntity.setTrangThai("Pending");

//        donHangEntity.setTongGiaTien(tongTien);

        // luu dia chi dat hang
        DiaChiEntity diaChi = new DiaChiEntity();
        donHangEntity.setDiaChiEntity(diaChi);
        diaChi.setDiaChi(reqDto.getDiaChiNhanHang());
        diaChi.setSdt(reqDto.getSoDienThoaiNhanHang());
        diaChi.setNguoiMuaId(reqDto.getNguoiTao());
        diaChi.setTenNguoiNhan(reqDto.getHoTenNguoiNhan());

        diaChiRepository.saveAndFlush(diaChi);
        donHangRepository.saveAndFlush(donHangEntity);

        // xoa gio hang sau khi dat thanh cong
        gioHangChiTietRepository.deleteAllById(reqDto.getGioHangItemIds());


        DonHangDto donHangDto = new DonHangDto();
        donHangDto.setId(donHangEntity.getId());
        donHangDto.setMaDonHang(donHangEntity.getMaDonHang());
        donHangDto.setTongSp(donHangEntity.getTongSp());
        donHangDto.setPhuongThucTT(donHangEntity.getPhuongThucTT());
        donHangDto.setTongGiaTien(donHangEntity.getTongGiaTien());
        donHangDto.setTongGiaCuoiCung(donHangEntity.getTongGiaCuoiCung());
        donHangDto.setNguoiMuaId(donHangEntity.getNguoiMuaId());
        donHangDto.setTrangThai(donHangEntity.getTrangThai());
        donHangDto.setNgayTao(donHangEntity.getNgayTao());


        List<ChiTietDonHangDto> chiTietDonHangDtos = new ArrayList<>();
        for (int i = 0; i < donHangEntity.getChiTietDonHangs().size(); i++) {
            ChiTietDonHangDto chiTietDonHangDto = new ChiTietDonHangDto();
            chiTietDonHangDto.setId(donHangEntity.getChiTietDonHangs().get(i).getId());
            chiTietDonHangDto.setSanPhamId(donHangEntity.getChiTietDonHangs().get(i).getSanPhamId());
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
        diaChiDto.setNguoiMuaId(diaChi.getNguoiMuaId());
        donHangDto.setDiaChi(diaChiDto);
        donHangDto.setChiTietDonHang(chiTietDonHangDtos);
        return donHangDto;
    }

    public String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Long getDonHang(Long id) {
        return null;
    }
}
