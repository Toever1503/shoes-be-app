package com.shoescms.domain.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class GioHangResDto {
    private List<?> sanPhamHienCo; // phan loai san pham hien so luong > 0
    private List<?> sanPhamHetHang; // phan loai san pham hien so luong <= 0
}
