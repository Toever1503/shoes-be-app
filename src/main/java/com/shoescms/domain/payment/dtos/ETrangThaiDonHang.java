package com.shoescms.domain.payment.dtos;

import lombok.Getter;

@Getter
public enum ETrangThaiDonHang {
    WAITING_CONFIRM("cho xac nhan"),
    ;

    ETrangThaiDonHang(String s) {
    }
}