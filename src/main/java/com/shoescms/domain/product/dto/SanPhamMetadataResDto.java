package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.SanPham;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamMetadataResDto {
    private Long id;
    private String tieuDe;
    private FileEntity anhChinh;

    public SanPhamMetadataResDto(SanPham sanPham) {
        this.id = sanPham.getId();
        this.tieuDe = sanPham.getTieuDe();
    }
}
