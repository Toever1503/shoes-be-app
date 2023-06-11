package com.shoescms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN(1, "ADMIN", "admin of store"),
    ROLE_STAFF(1, "STAFF", "staff"),
    ROLE_CUS(1, "CUS", "customer");

    private Integer id;
    private String title;
    private String desc;
}
