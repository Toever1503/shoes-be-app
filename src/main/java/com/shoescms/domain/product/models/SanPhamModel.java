package com.shoescms.domain.product.models;

import com.shoescms.domain.product.entitis.ThuongHieu;
import com.shoescms.domain.product.enums.ESex;
import com.shoescms.domain.product.entitis.DMGiay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SanPhamModel {
    private  Long  id;
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

    private Long anhChinh;

    private List<Long> anhPhu;
}
