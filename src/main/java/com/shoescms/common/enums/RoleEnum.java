package com.shoescms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN(1L, "ADMIN", "admin of store"),
    ROLE_STAFF(2L, "STAFF", "staff"),
    ROLE_USER(3L, "USER", "customer");

    private Long id;
    private String title;
    private String desc;
}
