package com.shoescms.domain.payment.dtos;

import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHangDto {
    private Long id;
    private Long sanPhamId;
    private Long phanLoaiSpId;
    private Integer soLuong;
    private BigDecimal giaTien;

    public static ChiTietDonHangDto toDto(ChiTietDonHangEntity chiTietDonHangEntity) {
        if (chiTietDonHangEntity == null) return null;
        return ChiTietDonHangDto.builder()
                .id(chiTietDonHangEntity.getId())
                .sanPhamId(chiTietDonHangEntity.getSanPhamId())
                .phanLoaiSpId(chiTietDonHangEntity.getPhanLoaiSpId())
                .soLuong(chiTietDonHangEntity.getSoLuong())
                .giaTien(chiTietDonHangEntity.getGiaTien())
                .build();
    }
}
