package com.shoescms.domain.shoes.dto;


import com.shoescms.domain.shoes.entitis.DMGiay;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMucDTO {

    private  Long id;

    private String tenDanhMuc;

    private  String slug;


    public static DanhMucDTO toDTO(DMGiay dm){
        if(dm ==null) return null;

        return
                DanhMucDTO.builder()
                        .id(dm.getId())
                        .tenDanhMuc(dm.getTenDanhMuc())
                        .slug(dm.getSlug())
                        .build();
    }
}
