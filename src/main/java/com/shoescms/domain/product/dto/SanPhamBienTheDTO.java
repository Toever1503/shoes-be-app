package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.BienTheGiaTri;
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
    private Integer soLuong;

    private BienTheGiaTriDTO giaTriObj1;
    private BienTheGiaTriDTO giaTriObj2;

    public  static SanPhamBienTheDTO toDTO(SanPhamBienThe sanPhamBienThe){
        return
                SanPhamBienTheDTO.builder()
                        .id(sanPhamBienThe.getId())
                        .bienThe1(sanPhamBienThe.getBienThe1())
                        .bienThe2(sanPhamBienThe.getBienThe2())
                        .giatri1(sanPhamBienThe.getBienTheGiaTri1())
                        .giatri2(sanPhamBienThe.getBienTheGiaTri2())
                        .soLuong(sanPhamBienThe.getSoLuong())
                        .build();
    }

    public SanPhamBienTheDTO setAnh(FileEntity anh) {
        this.anh = anh;
        return this;
    }

    public SanPhamBienTheDTO setGiaTriObj1(BienTheGiaTri giaTriObj1) {
        this.giaTriObj1 = BienTheGiaTriDTO.toDto(giaTriObj1);
        return this;
    }

    public SanPhamBienTheDTO setGiaTriObj2(BienTheGiaTri giaTriObj2) {
        this.giaTriObj2 = BienTheGiaTriDTO.toDto(giaTriObj2);;
        return this;
    }

}
