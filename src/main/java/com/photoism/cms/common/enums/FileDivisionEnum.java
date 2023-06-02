package com.photoism.cms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileDivisionEnum {
    tmp(0, "Temporary files", "Storage where files are stored by default."),
    community(1, "Community Files", "Storage where community files are stored."),
    popup(2, "Pop-up Files", "Storage where pop-up files are stored.");

    private Integer id;
    private String title;
    private String desc;
}
