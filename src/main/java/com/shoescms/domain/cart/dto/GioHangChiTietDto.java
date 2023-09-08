package com.shoescms.domain.cart.dto;


import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GioHangChiTietDto {
    private Long id;

    private Long gioHang;

    private Long sanPham;

    private Long sanPhamBienThe;

    private Integer soLuong;

    public static GioHangChiTietDto toDto(GioHangChiTiet gioHangChiTietDto){
        if(gioHangChiTietDto == null) return null;
        return GioHangChiTietDto.builder()
                .id(gioHangChiTietDto.getId())
                .gioHang(gioHangChiTietDto.getGioHang())
                .sanPham(gioHangChiTietDto.getSanPham())
                .sanPhamBienThe(gioHangChiTietDto.getSanPhamBienThe())
                .soLuong(gioHangChiTietDto.getSoLuong())
                .build();
    }
}
