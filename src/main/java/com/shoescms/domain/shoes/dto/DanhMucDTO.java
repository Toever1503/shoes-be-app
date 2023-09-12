package com.shoescms.domain.shoes.dto;


import com.shoescms.domain.shoes.entitis.DMGiay;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMucDTO {

    private  Long id;

    private String tenDanhMuc;

    private  String slug;

    private Integer soSp;


    public static DanhMucDTO toDTO(DMGiay dm){
        if(dm ==null) return null;

        return
                DanhMucDTO.builder()
                        .id(dm.getId())
                        .tenDanhMuc(dm.getTenDanhMuc())
                        .slug(dm.getSlug())
                        .build();
    }

    public DanhMucDTO setSoSp(Integer soSp) {
        this.soSp = soSp;
        return this;
    }

    public static List<DanhMucDTO> convertToTDO(List<DMGiay> dmGiay){
        return dmGiay.stream().map(s -> DanhMucDTO.builder()
                .id(s.getId())
                .tenDanhMuc(s.getTenDanhMuc())
                .slug(s.getSlug())
                .build()).collect(Collectors.toList());
    }

}
