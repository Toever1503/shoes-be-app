package com.shoescms.domain.voucher.dto;

import com.shoescms.domain.product.dto.DanhMucDTO;
import com.shoescms.domain.voucher.entity.EGiamGiaTheo;
import com.shoescms.domain.voucher.entity.VoucherEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VoucherDto {

    private Long id;

    private String maGiamGia;

    private Integer soLuotDaDung;

    private EGiamGiaTheo giamGiaTheo;

    private Double giaGiam;

    private Float phanTramGiam;

    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private LocalDateTime ngayXoa;

    private Long nguoiTao;

    private Long nguoiCapNhat;
    private Set<DanhMucDTO> danhMucList;
}
