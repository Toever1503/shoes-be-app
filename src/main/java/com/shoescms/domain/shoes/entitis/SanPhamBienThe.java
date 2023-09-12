package com.shoescms.domain.shoes.entitis;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_san_pham_bien_the")
public class SanPhamBienThe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_id", nullable = false, columnDefinition = "BIGINT COMMENT 'san pham '")
    private SanPham sanPham;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bien_the_id1", nullable = false, columnDefinition = "BIGINT COMMENT 'bien the '")
    private BienThe bienThe1;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bien_the_id2",  columnDefinition = "BIGINT COMMENT 'bien the '")
    private BienThe bienThe2;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "gia_tri_bt_id1", nullable = false, columnDefinition = "BIGINT COMMENT 'gia tri bien the '")
    private BienTheGiaTri bienTheGiaTri1;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "gia_tri_bt_id2", columnDefinition = "BIGINT COMMENT 'gia tri bien the '")
    private BienTheGiaTri bienTheGiaTri2;

    @Column(name = "anh")
    private String anh;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;
}
