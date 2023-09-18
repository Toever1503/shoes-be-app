package com.shoescms.domain.product.dto;


import com.shoescms.domain.product.entitis.ThuongHieu;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuongHieuDTO {

    private Long id;

    private String tenThuongHieu;

    private String slug;

    private Integer soSp;

    public static ThuongHieuDTO toDTO(ThuongHieu th) {
        if (th == null) return null;
        return ThuongHieuDTO.builder()
                .id(th.getId())
                .tenThuongHieu(th.getTenThuongHieu())
                .slug(th.getSlug())
                .build();
    }
    // convert 1 list ve DTo

    public ThuongHieuDTO setSoSp(Integer soSp) {
        this.soSp = soSp;
        return this;
    }

    public static List<ThuongHieuDTO> convertToTDO(List<ThuongHieu> thuongHieu) {
        return thuongHieu.stream().map(s -> ThuongHieuDTO.builder()
                        .id(s.getId())
                        .tenThuongHieu(s.getTenThuongHieu())
                        .slug(s.getSlug())
                        .build())
                .collect(Collectors.toList());
    }
}
