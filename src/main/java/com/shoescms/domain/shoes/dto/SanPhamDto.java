package com.shoescms.domain.shoes.dto;

import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.entitis.ThuongHieu;
import com.shoescms.domain.shoes.enums.ESex;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SanPhamDto {
    private Long id;

    private String maSP;

    private String tieuDe;

    private String moTa;

    private String slug;

    private BigDecimal giaCu;

    private BigDecimal giaMoi;

    private ESex gioiTinh;

    private String  anhBia;

    private ThuongHieu thuongHieu;

    private DMGiay dmGiay;

    private String nguoiTao;

    private String nguoiCapNhat;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private LocalDateTime ngayXoa;
    private Long anhChinh;
    private List<Long> anhPhu;

    public static SanPhamDto toDto(SanPham sanPham){
        if(sanPham == null) return null;
        return SanPhamDto.builder()
                .id(sanPham.getId())
                .maSP(sanPham.getMaSP())
                .tieuDe(sanPham.getTieuDe())
                .moTa(sanPham.getMoTa())
                .slug(sanPham.getSlug())
                .giaCu(sanPham.getGiaCu())
                .giaMoi(sanPham.getGiaMoi())
                .gioiTinh(sanPham.getGioiTinh())
                .anhBia(sanPham.getAnhBia())
                .thuongHieu(sanPham.getThuongHieu())
                .dmGiay(sanPham.getDmGiay())
                .nguoiTao(sanPham.getNguoiTao())
                .nguoiCapNhat(sanPham.getNguoiCapNhat())
                .ngayTao(sanPham.getNgayTao())
                .ngayCapNhat(sanPham.getNgayCapNhat())
                .ngayXoa(sanPham.getNgayXoa())
                .anhChinh(sanPham.getAnhChinh())
                .anhPhu(ObjectUtils.isEmpty(sanPham.getAnhPhu()) ? null : Arrays.stream(sanPham.getAnhPhu().split(",")).map(Long::valueOf).toList())
                .build();
    }
}
