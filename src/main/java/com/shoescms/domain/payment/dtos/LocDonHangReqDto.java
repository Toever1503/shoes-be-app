package com.shoescms.domain.payment.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class LocDonHangReqDto {
    private EPhuongThucTT phuongThucTT;
    private String status;
    private Date startOrderDate;
    private Date endOrderDate;
    private String maDonHang;
    private String nguoiMua;
    private String tenSanPham;
    private String ghiChu;
}
