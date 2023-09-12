package com.shoescms.domain.payment.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChiTietMaGiamGiaModel {

    private Long id;

    private Long donHangId;

    private Long maGiamGiaId;

    private LocalDateTime ngaySuDung;
}
