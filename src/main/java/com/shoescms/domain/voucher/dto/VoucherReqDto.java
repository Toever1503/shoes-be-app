package com.shoescms.domain.voucher.dto;

import com.shoescms.domain.voucher.entity.EGiamGiaTheo;
import com.shoescms.domain.voucher.entity.VoucherEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class VoucherReqDto {
    private Long id;

    private String maGiamGia;

    private Integer soLuotDaDung;

    private EGiamGiaTheo giamGiaTheo;

    private Double giaGiam;

    private Float phanTramGiam;

    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

}
