package com.shoescms.domain.admin.dto;

import com.shoescms.domain.admin.entitis.Voucher;
import com.shoescms.domain.admin.enums.EVoucher;
import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDTO {
    private Long id;

    private String maVoucher;

    private Date tuNgay;

    private Date denNgay;

    private String soLuong;

    private String menhGia;

    private EVoucher trangThai;

    public static VoucherDTO toDTO(Voucher voucher){
        if(voucher == null) return null;

        return VoucherDTO.builder()
                .id(voucher.getId())
                .maVoucher(voucher.getMaVoucher())
                .denNgay(voucher.getDenNgay())
                .tuNgay(voucher.getTuNgay())
                .soLuong(voucher.getSoLuong())
                .menhGia(voucher.getMenhGia())
                .trangThai(voucher.getTrangThai())
                .build();
    }
}
