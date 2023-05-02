package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CountryEnum {
    KR(1, "대한민국"),
    JP(2, "일본"),
    PH(3, "필리핀");

    private Integer id;
    private String desc;
}
