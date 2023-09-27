package com.shoescms.domain.product.dto;

import com.shoescms.common.model.FileEntity;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.enums.ESex;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class WebChiTietSanPhamDto {
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
    private List<BienTheGiaTriDTO> giaTri1List;
    private List<BienTheGiaTriDTO> giaTri2List;

    private Float soSaoDanhGia;
    private Integer soLuongKho;

    public static WebChiTietSanPhamDto toDto(SanPham sanPham) {
        if (sanPham == null) return null;
        return WebChiTietSanPhamDto.builder()
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
