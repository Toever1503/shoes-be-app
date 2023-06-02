package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommunityDivEnum {
    NOTICE(0, "Notice board", "Notice board."),
    REFERENCE(1, "Reference board", "Reference board.");

    private Integer id;
    private String title;
    private String desc;
}
