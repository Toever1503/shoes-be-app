package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SanPhamBienTheDTO {
    private Long id;

    private Long bienThe1;

    private Long bienThe2;

    private Long giatri1;

    private Long giatri2;

    private FileEntity anh;

    public  static SanPhamBienTheDTO toDTO(SanPhamBienThe sanPhamBienThe){
        return
                SanPhamBienTheDTO.builder()
                        .id(sanPhamBienThe.getId())
                        .bienThe1(sanPhamBienThe.getBienThe1())
                        .bienThe2(sanPhamBienThe.getBienThe2())
                        .giatri1(sanPhamBienThe.getBienTheGiaTri1())
                        .giatri2(sanPhamBienThe.getBienTheGiaTri2())
                        .build();
    }

    public SanPhamBienTheDTO setAnh(FileEntity anh) {
        this.anh = anh;
        return this;
    }
}
