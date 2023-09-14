package com.shoescms.domain.product.dto;


import com.shoescms.domain.product.entitis.BienThe;
import com.shoescms.domain.product.entitis.BienTheGiaTri;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BienTheGiaTriDTO {
    private Long id;

    private String giaTri;

    public static BienTheGiaTriDTO toDto(BienTheGiaTri bienTheGiaTri) {
        return BienTheGiaTriDTO
                .builder()
                .id(bienTheGiaTri.getId())
                .giaTri(bienTheGiaTri.getGiaTri())
                .build();
    }
}
