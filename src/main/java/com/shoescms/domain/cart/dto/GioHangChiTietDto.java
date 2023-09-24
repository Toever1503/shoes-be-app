package com.shoescms.domain.cart.dto;


import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.product.dto.SanPhamBienTheDTO;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GioHangChiTietDto {
    private Long id;

    private SanPhamBienTheDTO sanPhamBienThe;

    private Integer soLuong;

    private LocalDateTime ngayCapNhat;

    public static GioHangChiTietDto toDto(GioHangChiTiet gioHangChiTiet){
        if(gioHangChiTiet == null) return null;
        return GioHangChiTietDto.builder()
                .id(gioHangChiTiet.getId())
                .soLuong(gioHangChiTiet.getSoLuong())
                .ngayCapNhat(gioHangChiTiet.getNgayCapNhat())
                .build();
    }

    public GioHangChiTietDto setSanPhamBienThe(SanPhamBienThe sanPhamBienThe) {
        this.sanPhamBienThe = SanPhamBienTheDTO.toDTO(sanPhamBienThe);
        return this;
    }
}
