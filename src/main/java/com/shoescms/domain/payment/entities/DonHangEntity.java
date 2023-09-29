package com.shoescms.domain.payment.entities;

import com.shoescms.domain.payment.dtos.EPhuongThucTT;
import com.shoescms.domain.payment.dtos.ETrangThaiDonHang;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
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

    @Column(name = "tong_tien_sp", nullable = false)
    @ColumnDefault("0")
    private BigDecimal tongTienSP;

    @Column(name = "tong_gia_tien", nullable = false)
    private BigDecimal tongGiaTien;

    @Column(name = "tong_gia_cuoi_cung", nullable = false)
    private BigDecimal tongGiaCuoiCung;

    @Column(name = "nguoi_mua_id")
    private Long nguoiMuaId;

    @Column(name = "nguoi_cap_nhat")
    private Long nguoiCapNhat;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private ETrangThaiDonHang trangThai;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Where(clause = "ngay_xoa is null")
    private List<ChiTietDonHangEntity> chiTietDonHangs;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dia_chi_id", nullable = false)
    private DiaChiEntity diaChiEntity;

}
