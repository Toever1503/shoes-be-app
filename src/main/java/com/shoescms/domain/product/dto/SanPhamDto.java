package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.ThuongHieu;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.enums.ESex;
import com.shoescms.domain.product.entitis.DMGiay;
import com.shoescms.domain.product.entitis.SanPham;
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

    private Long nguoiTao;

    private Long nguoiCapNhat;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private LocalDateTime ngayXoa;
    private FileEntity anhChinh;
    private List<Long> anhPhu;

    private ELoaiBienThe loaiBienThe;
    private Boolean hienThiWeb;
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
                .thuongHieu(sanPham.getThuongHieu())
                .dmGiay(sanPham.getDmGiay())
                .nguoiTao(sanPham.getNguoiTao())
                .nguoiCapNhat(sanPham.getNguoiCapNhat())
                .ngayTao(sanPham.getNgayTao())
                .ngayCapNhat(sanPham.getNgayCapNhat())
                .ngayXoa(sanPham.getNgayXoa())
                .anhPhu(ObjectUtils.isEmpty(sanPham.getAnhPhu()) ? null : Arrays.stream(sanPham.getAnhPhu().split(",")).map(Long::valueOf).toList())
                .loaiBienThe(sanPham.getLoaiBienThe())
                .hienThiWeb(sanPham.getHienThiWeb())
                .build();
    }

    public SanPhamDto setAnhChinh(FileEntity anhChinh) {
        this.anhChinh = anhChinh;
        return this;
    }
}
