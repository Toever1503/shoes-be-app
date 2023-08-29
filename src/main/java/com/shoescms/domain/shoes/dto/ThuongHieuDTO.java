package com.shoescms.domain.shoes.dto;


import com.shoescms.domain.shoes.entitis.ThuongHieu;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuongHieuDTO {

    private  Long id;

    private String tenThuongHieu;

    private  String slug;

    public static  ThuongHieuDTO toDTO(ThuongHieu th){
        if(th==null) return  null;
        return ThuongHieuDTO.builder()
                .id(th.getId())
                .tenThuongHieu(th.getTenThuongHieu())
                .slug(th.getSlug())
                .build();
    }
}
