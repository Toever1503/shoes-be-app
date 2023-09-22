package com.shoescms.domain.payment.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class DonHangDto {
    private Long id;
    private String maDonHang;
    private Integer tongSp;
    private EPhuongThucTT phuongThucTT;
    private BigDecimal tongGiaTien;
    private BigDecimal tongGiaCuoiCung;
    private Long nguoiMuaId;
    private String trangThai;
    private Date ngayTao;
    private DiaChiDto diaChi;
    private List<ChiTietDonHangDto>  chiTietDonHang;
}
