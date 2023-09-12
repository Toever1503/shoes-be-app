package com.shoescms.domain.payment.dtos;


import com.shoescms.domain.payment.entities.ChiTietMaGiamGiaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChiTietMaGiamGiaDto {

    private Long id;

    private Long donHangId;

    private Long maGiamGiaId;

    private LocalDateTime ngaySuDung;

    public static ChiTietMaGiamGiaDto toDto(ChiTietMaGiamGiaEntity entity) {
        return ChiTietMaGiamGiaDto.builder()
                .id(entity.getId())
                .donHangId(entity.getDonHangId())
                .maGiamGiaId(entity.getMaGiamGiaId())
                .ngaySuDung(entity.getNgaySuDung())
                .build();
    }
}
