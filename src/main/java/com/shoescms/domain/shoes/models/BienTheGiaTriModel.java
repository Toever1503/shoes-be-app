package com.shoescms.domain.shoes.models;


import com.shoescms.domain.shoes.entitis.BienThe;

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
