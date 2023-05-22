package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodeGroupEnum {
    ROLE(1, "역할"),
    COUNTRY(2, "국가"),
    CITY(3, "지역"),
    BRAND(4, "브랜드"),
    STORE_TYPE(5, "상점 유형"),
    DEPARTMENT(6, "소속"),
    PRIVILEGE(7, "권한");

    private Integer id;
    private String desc;
}
