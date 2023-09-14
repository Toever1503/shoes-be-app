package com.shoescms.domain.product.dto;

import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SanPhamBienTheDTO {
    private Long id;

    private Long bienThe1;

    private Long bienThe2;

    private Long bienTheGiaTri1;

    private Long bienTheGiaTri2;

    private Long anh;

    private int soLuong;

    private LocalDateTime ngayXoa;

    public  static SanPhamBienTheDTO toDTO(SanPhamBienThe sanPhamBienThe){
        if (sanPhamBienThe==null) return  null;
        return
                SanPhamBienTheDTO.builder().id(sanPhamBienThe.getId())
                        .bienThe1(sanPhamBienThe.getBienThe1())
                        .bienThe2(sanPhamBienThe.getBienThe2())
                        .bienTheGiaTri1(sanPhamBienThe.getBienTheGiaTri1())
                        .bienTheGiaTri2(sanPhamBienThe.getBienTheGiaTri2())
                        .anh(sanPhamBienThe.getAnh())
                        .soLuong(sanPhamBienThe.getSoLuong())
                        .ngayXoa(sanPhamBienThe.getNgayXoa())
                        .build();
    }

}
