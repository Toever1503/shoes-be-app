package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodeGroupEnum {
    ROLE(1, "사용자 권한"),
    COUNTRY(2, "국가"),
    CITY(3, "지역"),
    BRAND(4, "브랜드"),
    STORE_TYPE(5, "상점 유형");

    private Integer id;
    private String desc;
}
