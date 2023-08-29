package com.shoescms.domain.payment.entities;

import com.shoescms.domain.payment.dtos.EPhuongThucTT;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "tb_don_hang")
public class DonHangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "ma_don_hang", length = 50, unique = true, nullable = false)
    private String maDonHang;

    @Column(name = "tong_sp", nullable = false)
    private Integer tongSp;

    @Column(name = "phuong_thuc_tt", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPhuongThucTT phuongThucTT;

    @Column(name = "tong_gia_tien", nullable = false)
    private Double tongGiaTien;

    @Column(name = "tong_gia_cuoi_cung", nullable = false)
    private Double tongGiaCuoiCung;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dia_chi_id")
    private DiaChiEntity diaChi;

    @Column(name = "nguoi_mua_id", nullable = false)
    private Long nguoiMuaId;

    @Column(name = "nguoi_cap_nhat", nullable = false)
    private Long nguoiCapNhat;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "ngay_tao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date ngayCapNhat;

    @Column(name = "ngay_xoa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayXoa;

    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "ngayXoa is null")
    private List<ChiTietDonHangEntity> chiTietDonHangs;
}
