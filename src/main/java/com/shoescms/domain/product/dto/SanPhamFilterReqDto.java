package com.shoescms.domain.product.dto;

import lombok.Data;

@Data
public class SanPhamFilterReqDto {

    private String tieuDe;
    private Long dmGiay;
    private Long thuongHieu;
    private Boolean hienThiWeb;
}
