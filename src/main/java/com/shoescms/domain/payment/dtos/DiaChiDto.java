package com.shoescms.domain.payment.dtos;

import lombok.Data;

@Data
public class DiaChiDto {
    private Long id;
    private String tenNguoiNhan;
    private String sdt;
    private String diaChi;
    private Long nguoiMuaId;
}
