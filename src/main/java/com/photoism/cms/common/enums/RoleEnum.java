package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_SUPER_ADMIN(0, "SUPER_ADMIN", "시스템 관리자"),
    ROLE_ADMIN(1, "ADMIN", "운영자"),
    ROLE_ADMIN_KR(2, "ADMIN_KR", "운영자_한국"),
    ROLE_ADMIN_JP(3, "ADMIN_JP", "운영자_일본"),
    ROLE_ADMIN_PH(4, "ADMIN_PH", "운영자_필리핀"),
    ROLE_OPERATION(5, "OPERATION", "운영팀"),
    ROLE_MARKETING(6, "MARKETING", "마케팅팀"),
    ROLE_LOGISTICS(7, "LOGISTICS", "물류팀"),
    ROLE_FINANCE(8, "FINANCE", "재무팀"),
    ROLE_STORE_OWNER(9, "STORE_OWNER", "상점_점주"),
    ROLE_STORE_STAFF(10, "STORE_STAFF", "상점_직원");

    private Integer id;
    private String title;
    private String desc;
}
