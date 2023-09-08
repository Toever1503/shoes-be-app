package com.shoescms.domain.cart.dto;

import com.shoescms.domain.cart.entity.GioHang;
import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GioHangDto {

    private Long id;

    private Long userEntity;

    public static GioHangDto toDto(GioHang gioHang){
        if(gioHang == null) return null;
        return GioHangDto.builder()
                .id(gioHang.getId())
                .userEntity(gioHang.getUserEntity())
                .build();
    }
}
