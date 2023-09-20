package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.DMGiay;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.ThuongHieu;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.enums.ESex;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ChiTietSanPhamDto {
    private Long id;

    private String maSP;

    private String tieuDe;

    private String moTa;

    private String slug;

    private BigDecimal giaCu;

    private BigDecimal giaMoi;

    private ESex gioiTinh;

    private ThuongHieuDTO thuongHieu;

    private DanhMucDTO dmGiay;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private FileEntity anhChinh;
    private List<FileEntity> anhPhu;

    private ELoaiBienThe loaiBienThe;
    private Boolean hienThiWeb;

    private List<SanPhamBienTheDTO> bienTheDTOS;

    public static ChiTietSanPhamDto toDto(SanPham sanPham) {
        if (sanPham == null) return null;
        return ChiTietSanPhamDto.builder()
                .id(sanPham.getId())
                .maSP(sanPham.getMaSP())
                .tieuDe(sanPham.getTieuDe())
                .moTa(sanPham.getMoTa())
                .slug(sanPham.getSlug())
                .giaCu(sanPham.getGiaCu())
                .giaMoi(sanPham.getGiaMoi())
                .gioiTinh(sanPham.getGioiTinh())
                .thuongHieu(ThuongHieuDTO.toDTO(sanPham.getThuongHieu()))
                .dmGiay(DanhMucDTO.toDTO(sanPham.getDmGiay()))
                .ngayTao(sanPham.getNgayTao())
                .ngayCapNhat(sanPham.getNgayCapNhat())
                .loaiBienThe(sanPham.getLoaiBienThe())
                .hienThiWeb(sanPham.getHienThiWeb())
                .build();
    }
}
