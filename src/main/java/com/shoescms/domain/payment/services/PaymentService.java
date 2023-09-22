package com.shoescms.domain.payment.services;

import com.shoescms.common.exception.CommonMessageException;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.repository.GioHangChiTietRepository;
import com.shoescms.domain.payment.dtos.DatHangReqDto;
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
    public void datHang(DatHangReqDto reqDto) {
        // lay ra thong tin cac san pham dat
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findAllById(reqDto.getGioHangItemIds());

        // luu thong tin don hang
        DonHangEntity donHangEntity = new DonHangEntity();
        //ma don hang
        donHangEntity.setMaDonHang(getRandomNumber(30));

        BigDecimal tongTien = new BigDecimal(0);
        // luu thong tin chi tiet don hang
        List<ChiTietDonHangEntity> chiTietDonHangEntities = new ArrayList<>();
        for (int i = 0; i < gioHangChiTiets.size(); i++) {
            SanPham sanPham = sanPhamRepository.findById(gioHangChiTiets.get(i).getSanPham()).orElseThrow(() -> new ObjectNotFoundException(8));

            BigDecimal tongTienSp =  sanPham.getGiaMoi().multiply(BigDecimal.valueOf(gioHangChiTiets.get(i).getSoLuong().doubleValue()));
            tongTien.add(tongTienSp);

            // tao thong tin
            ChiTietDonHangEntity chiTietDonHang = new ChiTietDonHangEntity();
            chiTietDonHang.setDonHang(donHangEntity);
            chiTietDonHang.setSoLuong(chiTietDonHang.getSoLuong());
            chiTietDonHang.setGiaTien(chiTietDonHang.getGiaTien());
//            chiTietDonHang.setPhanLoaiSpId(gioHangChiTiets.);


            chiTietDonHangEntities.add(chiTietDonHang);
        }
        donHangEntity.setChiTietDonHangs(chiTietDonHangEntities);

//        donHangEntity.setTongGiaTien(tongTien);

        // luu dia chi dat hang
        DiaChiEntity diaChi = new DiaChiEntity();
//        diaChiRepository.saveAndFlush(diaChi);
//        donHangRepository.saveAndFlush(donHangEntity)

        // xoa gio hang sau khi dat thanh cong
//        gioHangChiTietRepository.deleteAllById(reqDto.getGioHangItemIds());

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
