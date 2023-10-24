package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.SanPhamEntity;
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

    public SanPhamMetadataResDto(SanPhamEntity sanPhamEntity) {
        this.id = sanPhamEntity.getId();
        this.tieuDe = sanPhamEntity.getTieuDe();
    }

    public static SanPhamMetadataResDto toDto(SanPhamEntity sanPhamEntity) {
        if (sanPhamEntity == null) return null;
        return SanPhamMetadataResDto
                .builder()
                .id(sanPhamEntity.getId())
                .tieuDe(sanPhamEntity.getTieuDe())
                .build();
    }
}
