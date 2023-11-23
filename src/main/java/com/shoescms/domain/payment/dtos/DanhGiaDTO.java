package com.shoescms.domain.payment.dtos;

import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import com.shoescms.domain.payment.entities.DanhGia;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DanhGiaDTO {
    private Long id;

    private Long donHangChiTietId;

    private Long nguoiTaoId;

    private String binhLuan;

    private Integer soSao;

    private LocalDateTime ngayTao;

    public static DanhGiaDTO toDto(DanhGia danhGia) {
        if (danhGia == null) return null;
        return DanhGiaDTO.builder()
                .id(danhGia.getId())
                .donHangChiTietId(danhGia.getDonHangChiTietId())
                .nguoiTaoId(danhGia.getNguoiTaoId())
                .binhLuan(danhGia.getBinhLuan())
                .soSao(danhGia.getSoSao())
                .ngayTao(danhGia.getNgayTao())
                .build();
    }
}
