package com.shoescms.domain.shoes.entitis;

import com.shoescms.common.model.BaseDateEntity;
import com.shoescms.domain.shoes.enums.ESex;
import com.shoescms.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_san_pham")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class SanPham  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma_sp")
    private String maSP;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "slug")
    private String slug;

    @Column(name = "gia_cu")
    private BigDecimal giaCu;

    @Column(name = "gia_moi")
    private BigDecimal giaMoi;

    @Column(name = "gioi_tinh")
    @Enumerated(EnumType.STRING)
    private ESex gioiTinh;

    @Column(name = "anh_bia")
    private String  anhBia;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "thuong_hieu_id",nullable = false, columnDefinition = "BIGINT COMMENT 'Shoes thuong hieu'")
    private ThuongHieu thuongHieu;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "danh_muc_id",nullable = false, columnDefinition = "BIGINT COMMENT 'Shoes danh muc'")
    private DMGiay dmGiay;

    @Column(name = "nguoi_tao")
    private Long nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private Long nguoiCapNhat;

    @Column(name = "ngay_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ngayCapNhat;

    @Column(name = "ngay_xoa")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ngayXoa;

    @Column(name = "anh_chinh")
    private Long anhChinh;

    @Column(name = "anh_phu")
    private String anhPhu;
}
