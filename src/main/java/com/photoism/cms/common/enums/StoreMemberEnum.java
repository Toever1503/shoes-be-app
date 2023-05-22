package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoreMemberEnum {
    OWNER(1, "OWNER", "점주"),
    STAFF(2, "STAFF", "직원");

    private Integer id;
    private String title;
    private String desc;
}
