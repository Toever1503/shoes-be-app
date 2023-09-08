package com.shoescms.domain.shoes.models;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMucGiayModel {

    private Long id;

    private String tenDanhMuc;

    private String slug;
}
