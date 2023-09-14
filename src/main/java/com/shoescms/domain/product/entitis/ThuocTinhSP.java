package com.shoescms.domain.product.entitis;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@Builder
@Table(name = "tbl_thuoc_tinh_san_pham")
public class ThuocTinhSP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_id", nullable = false, columnDefinition = "BIGINT COMMENT 'thuoc tinh san pham'")
    private SanPham sanPham;

    @Column(name = "ten_thuoc_tinh")
    private String tenThuocTinh;

    @Column(name = "gia_tri")
    private String giaTri;
}
