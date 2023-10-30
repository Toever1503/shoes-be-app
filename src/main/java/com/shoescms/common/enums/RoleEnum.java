package com.shoescms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN(1, "ROLE_ADMIN", "admin of store"),
    ROLE_STAFF(3, "ROLE_STAFF", "staff"),
    ROLE_USER(2, "ROLE_USER", "customer");

    private Integer id;
    private String title;
    private String desc;
}
