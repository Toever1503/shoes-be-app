package com.shoescms.domain.product.dto;

import lombok.Data;

@Data
public class SanPhamFilterReqDto {

    private String q;
    private String tieuDe;
    private String maSp;
    private Long dmGiay;
    private Long thuongHieu;
    private Boolean hienThiWeb;
}
