package com.shoescms.domain.shoes.models;


import com.shoescms.domain.shoes.entitis.BienThe;
import com.shoescms.domain.shoes.entitis.BienTheGiaTri;
import com.shoescms.domain.shoes.entitis.SanPham;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SanPhamBienTheModel {
    private Long id;

    private SanPham sanPham;

    private BienThe bienThe;

    private BienTheGiaTri bienTheGiaTri;

    private String anh;

    private int soLuong;

    private LocalDateTime ngayXoa;
}
