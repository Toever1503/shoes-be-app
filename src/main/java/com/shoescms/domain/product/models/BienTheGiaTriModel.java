package com.shoescms.domain.product.models;


import com.shoescms.domain.product.entitis.BienThe;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BienTheGiaTriModel {
    private Long id;

    private BienThe bienThe;

    private String giaTri;
}
