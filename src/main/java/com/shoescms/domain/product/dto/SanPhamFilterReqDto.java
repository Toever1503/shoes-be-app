package com.shoescms.domain.product.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SanPhamFilterReqDto {

    private String q;
    private String tieuDe;
    private String maSp;
    private Long dmGiay;
    private Long thuongHieu;
    private Boolean hienThiWeb;
    private List<LocalDateTime> createdAtRange;
}
