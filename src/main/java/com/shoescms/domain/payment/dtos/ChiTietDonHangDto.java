package com.shoescms.domain.payment.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChiTietDonHangDto {
    private Long id;
    private Long sanPhamId;
    private Long phanLoaiSpId;
    private Integer soLuong;
    private BigDecimal giaTien;
}
