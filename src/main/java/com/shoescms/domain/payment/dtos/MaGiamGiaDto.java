package com.shoescms.domain.payment.dtos;

import com.shoescms.domain.payment.entities.MaGiamGiaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaGiamGiaDto {

    private Long id;

    private String maCode;

    private Integer soLuong;

    private BigDecimal soTien;

    private BigDecimal soTienToiThieu;

    private LocalDateTime ngayBatDau;

    private LocalDateTime ngayKetThuc;

    private Long nguoiTao;

    private Long nguoiCapNhat;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private LocalDateTime ngayXoa;

    public static MaGiamGiaDto toDto(MaGiamGiaEntity entity) {
        if (entity == null) {
            return null;
        }
        return MaGiamGiaDto.builder()
                .id(entity.getId())
                .maCode(entity.getMaCode())
                .soLuong(entity.getSoLuong())
                .soTien(entity.getSoTien())
                .soTienToiThieu(entity.getSoTienToiThieu())
                .ngayBatDau(entity.getNgayBatDau())
                .ngayKetThuc(entity.getNgayKetThuc())
                .nguoiTao(entity.getNguoiTao())
                .nguoiCapNhat(entity.getNguoiCapNhat())
                .ngayTao(entity.getNgayTao())
                .ngayCapNhat(entity.getNgayCapNhat())
                .ngayXoa(entity.getNgayXoa())
                .build();
    }
}
