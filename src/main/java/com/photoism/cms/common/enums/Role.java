package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ROLE_SUPER_ADMIN(1, "super_admin", "시스템 관리자"),
    ROLE_ADMIN(2, "admin", "총괄/운영자"),
    ROLE_BRANCH(3, "branch", "직영/가맹점");

    private Integer id;
    private String title;
    private String desc;
}
