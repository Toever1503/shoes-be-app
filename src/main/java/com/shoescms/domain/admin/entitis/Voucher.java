package com.shoescms.domain.admin.entitis;

import com.shoescms.domain.admin.enums.EVoucher;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "tbl_voucher")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma_voucher")
    private String maVoucher;

    @Column(name = "tu_ngay")
    private Date tuNgay;

    @Column(name = "den_ngay")
    private Date denNgay;

    @Column(name = "so_luong")
    private String soLuong;

    @Column(name = "den_ngay")
    private String menhGia;

    @Column(name = "trang_thai")
    private EVoucher trangThai;
}
