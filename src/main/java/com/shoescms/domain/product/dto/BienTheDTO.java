package com.shoescms.domain.product.dto;


import com.shoescms.domain.product.entitis.BienThe;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BienTheDTO {
    private Long id;
    private String tenBienThe;

    public static BienTheDTO toDto(BienThe bienThe){
        if(bienThe == null) return  null;
        return BienTheDTO
                .builder()
                .id(bienThe.getId())
                .tenBienThe(bienThe.getTenBienThe())
                .build();
    }
}
