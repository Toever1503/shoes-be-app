package com.shoescms.domain.admin.models;

import com.shoescms.domain.admin.enums.EVoucher;
import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherModel {
    private Long id;

    private String maVoucher;

    private Date tuNgay;

    private Date denNgay;

    private String soLuong;

    private String menhGia;

    private EVoucher trangThai;
}
