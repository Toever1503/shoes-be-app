package com.shoescms.domain.cart.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_gio_hang_chi_tiet")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class GioHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gio_hang")
    private Long gioHang;

    @Column(name = "san_pham")
    private Long sanPham;

    @Column(name = "san_pham_bien_the")
    private Long sanPhamBienThe;

    @Column(name = "so_luong")
    private Integer soLuong;
}
