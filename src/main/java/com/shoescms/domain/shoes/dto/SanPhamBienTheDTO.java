package com.shoescms.domain.shoes.dto;

import com.shoescms.domain.shoes.entitis.BienThe;
import com.shoescms.domain.shoes.entitis.BienTheGiaTri;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SanPhamBienTheDTO {
    private Long id;

    private SanPham sanPham;

    private Long bienThe;

    private BienTheGiaTri bienTheGiaTri;

    private String anh;

    private int soLuong;

    private LocalDateTime ngayXoa;

    public  static SanPhamBienTheDTO toDTO(SanPhamBienThe sanPhamBienThe){
        if (sanPhamBienThe==null) return  null;
        return
                SanPhamBienTheDTO.builder().id(sanPhamBienThe.getId())
                        .bienThe(sanPhamBienThe.getBienThe().getId())
                 //       .sanPham(sanPhamBienThe.getSanPham())
                //        .bienTheGiaTri(sanPhamBienThe.getBienTheGiaTri())
                        .anh(sanPhamBienThe.getAnh())
                        .soLuong(sanPhamBienThe.getSoLuong())
                        .ngayXoa(sanPhamBienThe.getNgayXoa())
                        .build();
    }

}
