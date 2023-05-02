package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_SUPER_ADMIN(0, "SUPER_ADMIN", "시스템 관리자"),
    ROLE_ADMIN(1, "ADMIN", "운영자"),
    ROLE_STORE(2, "STORE", "상점"),
    ROLE_MARKETER(3, "MARKETER", "마케터");

    private Integer id;
    private String title;
    private String desc;
}
