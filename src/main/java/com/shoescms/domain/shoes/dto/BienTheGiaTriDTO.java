package com.shoescms.domain.shoes.dto;


import com.shoescms.domain.shoes.entitis.BienThe;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BienTheGiaTriDTO {
    private Long id;

    private BienThe bienThe;

    private String giaTri;
}
